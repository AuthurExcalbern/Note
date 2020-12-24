从一个最简单的例子看起：

```kotlin
fun main() {

    GlobalScope.launch {
        println("协程中的线程是：" + Thread.currentThread().name)
    }

    // 等待1秒钟，让协程执行完
    Thread.sleep(1000)
}
```

这段代码是在协程中打印所在线程的名称，其中 `GlobalScope` 是协程默认的全局作用域，`launch` 是一个协程构造器，它创建了一个协程并自动执行。下面我们详细了解下协程的这三个部分：
 (1) 协程作用域(CoroutineScope)；
 (2)  协程构造器；
 (3) 协程上下文(CoroutineContext) 和调度器(CoroutineDispatcher)；

------

## 1. 协程作用域

例子中的 GlobalScope 虽然首字母大写，但它是一个单例对象，是默认的全局作用域。
 GlobalScope 实现了 `CoroutineScope` 接口，这个接口持有了协程上下文，定义如下：

```kotlin
public interface CoroutineScope {
    public val coroutineContext: CoroutineContext
}
```

### 1.1 协程作用域的作用

作用域的主要作用是满足结构化并发的需求。
 每个线程启动后，它执行的上下文就是整个进程，没有线程独立的作用域和任务边界。
 但对于协程，我们很少需要一个全局的协程，协程总是与应用程序中的某个局部作用域相关，这个局部作用域是一个生命周期有限的实体，例如一次网络加载、一个 Activity 获 Fragment。
 想更好地理解什么是「结构化并发」可以看这篇文章：[《什么是结构化并发 》](https://www.jianshu.com/p/3dc8ba43c28b)。

### 1.2 如何自定义作用域？

协程作用域的创建方式有很多，常见的有：
 ① 继承 CoroutineScope 接口自己实现；
 ② 使用 coroutineScope 方法创建；
 ③ 使用 supervisorScope 方法创建；

下面分别看看：

**(1) 继承 CoroutineScope 接口实现自定义的作用域**
 如果你有一个业务相对独立的类，你可以继承 CoroutineScope 接口，使得你这个类成为一个协程的作用域。
 例如 Android 开发中每一个 Activity 都可以是一个作用域：

```kotlin
class MainActivity: AppCompatActivity(), CoroutineScope {

    /** 需要实现协程上线文，这里使用空上下文 */
    override val coroutineContext = EmptyCoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 在默认的子线程中，请求网络数据
        launch {
            val res = requestService()

            // 在主线程中，更新 UI
            launch(Dispatchers.Main) {
                updateUi(res)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // 在 Activity 销毁时取消协程
        cancel()
    }
}
```

为了在 Android/JavaFx 等场景中更方便的使用，官方提供了 MainScope() 方法快速创建基于主线程的协程作用域。
 只需要将 CoroutineScope 的实现通过 by 关键字委托给 MainScope 对象即可：

**(2) 使用 MainScope 创建协程作用域**

```kotlin
class MainActivity: AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 在IO线程中，请求网络数据
        launch(Dispatchers.IO) {
            val res = requestService()

            // 在主线程中，更新 UI
            launch {
                updateUi(res)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // 在 Activity 销毁时取消
        cancel()
    }
}
```

**(3) 使用 coroutineScope 和 supervisorScope 方法创建协程作用域**
 coroutineScope 方法可以用来创建一个子作用域，它只能在另一个已有的协程作用域中调用，例如在另外一个 suspend 方法中调用。
 supervisorScope 方法和 coroutineScope 类似，也用于创建一个子作用域，
 区别是 supervisorScope 出现异常时不影响其他子协程， coroutineScope 出现异常时会把异常抛出。
 篇幅所限，不举例子了。

------

## 2. 协程构造器

例子中的 launch 就是一个携程构造器，利用协程构造器可以方便地构造出一个协程对象。
 在创建线程时，我们可以这样创建：

```kotlin
import kotlin.concurrent.thread
thread {
    // 在子线程中执行的代码
}
```

同样，创建协程时，我们可以利用 `launch`、`async`、`runBlocking`、`withContext` 等构造器创建协程，例如：

```kotlin
GlobalScope.launch {
    // 在协程中执行的代码
}
```

### 2.1 launch

`launch` 是最常见的协程构建器，它会启动一个新的协程(AbstractCoroutine)，并将这个协程对象返回，接着会在协程中执行参数中的 block。
 AbstractCoroutine 继承了 Job，launch 返回的 Job 对象实际就是协程对象本身。

launch 的原型如下：

```kotlin
public fun CoroutineScope.launch(
    /** 上下文 */
    context: CoroutineContext = EmptyCoroutineContext,
    
    /** 如何启动 */
    start: CoroutineStart = CoroutineStart.DEFAULT,
    
    /** 启动后要执行的代码 */
    block: suspend CoroutineScope.() -> Unit
): Job
```

launch 方法有两个可选参数：CoroutineContext 和 CoroutineStart。
 **CoroutineContext：**
 是协程的上下文，默认使用 EmptyCoroutineContext，作用是决定把协程派发到哪个线程中执行，下文会介绍。
 **CoroutineStart：**
 是启动时刻的枚举，默认使用 CoroutineStart.DEFAULT，表示尽快执行。

### 2.2 async

`async` 比较常见，它也会启动新的协程(AbstractCoroutine)，并返回这个协程对象，然后在协程中执行 block。
 返回类型 Deferred 继承自 Job，与 Job 的区别是 Job 不会携带返回值， Deferred 带了返回值。
 所以 async 多用于需要返回结果的场景。

async 的函数原型：

```kotlin
public fun <T> CoroutineScope.async(
    /** 上下文 */
    context: CoroutineContext = EmptyCoroutineContext,
    
    /** 如何启动 */
    start: CoroutineStart = CoroutineStart.DEFAULT,
    
    /** 启动后要执行的代码 */
    block: suspend CoroutineScope.() -> Unit
): Deferred<T>
```

参数和 launch 一样，我们看看 async 怎么获取返回值：

```kotlin
// 任务1：耗时一秒后返回100
val coroutine1 = GlobalScope.async {
    delay(1000)
    return@async 100
}

// 任务2：耗时1秒后返回200
val coroutine2 = GlobalScope.async {
    delay(1000)
    return@async 200
}

// 上面两个协程会并发执行

// 等待两个任务都执行完毕后，再继续下一步（打印结果）。
GlobalScope.launch {
    val v1 = coroutine1.await()
    val v2 = coroutine2.await()
    log("执行的结果,v1 = $v1, v2=$v2")
}
```

`async` 还有更多用法，后面会介绍。

### 2.3 runBlocking

`runBlocking` 会启动新的协程(AbstractCoroutine)，并返回这个协程对象，然后在协程中执行 block。
 与 `launch` 和 `async` 不同的是，`runBlocking` 会阻塞住当前线程，直到 block 执行完毕。

`runBlocking` 不应该在协程中调用，它大多数使用场景是为了测试。

runBlocking 的函数原型：

```kotlin
public fun <T> runBlocking(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> T
): T
```

### 2.4 withContext

`withContext` 会在调用处挂起，直到 block 执行完毕。它需要指定一个协程上下文，block 会在这个上下文中执行。

`withContext` 的函数原型：

```kotlin
public suspend fun <T> withContext(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> T
): T
```

使用示例：

```kotlin
runBlocking {

    launch {
        delay(1000)
        log("第 1 处的代码执行完毕！")
    }

    withContext(GlobalScope.coroutineContext) {
        log("第 2 处的代码开始执行！")
        delay(2000)
        log("第 2 处的代码执行完毕！")
    }

    log("外部代码执行完毕！")
}
```

上面代码的输出结果是：

> [Thread: 1  ] [12:32:23.772] 协程 2 中的代码开始执行！
>  [Thread: 1  ] [12:32:24.784] 协程 1 中的代码执行完毕！
>  [Thread: 1  ] [12:32:25.779] 协程 2 中的代码执行完毕！
>  [Thread: 1  ] [12:32:25.779] 外部代码执行完毕！

可以看到，`withContext` 调用后，后续的代码会等到 `withContext` 的 block 执行完毕后再执行。 同时，线程是没有被阻塞的。

------

## 3. 协程上下文 和 调度器

`CoroutineContext` 是一个接口，定义协程的上下文。
 `CoroutineDispatcher` 负责决定将协程放到哪个线程中去执行。

调度器(CoroutineDispatcher) 是上下文(CoroutineContext) 的子类。

Kotlin 提供了默认的几个调度器，放在 Dispatchers 中，他们分别是：

### 3.1 Dispatchers 中包含的调度器

**(1) Dispatchers.Default**
 默认的派发器。它会使用后台共享的线程池。

**(2) Dispatchers.Main**
 使用主线程。只有在 Android、JavaFx 等平台才有。需要引入对应的依赖：

```groovy
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5'
```

**(3) Dispatchers.Unconfined**
 这个协程派发器会在调用者线程内启动协程, 但只会持续运行到第一次挂起点为止。
 在挂起之后, 它会在哪个线程内恢复执行, 完全由被调用的挂起函数来决定。
 非受限派发器(Unconfined dispatcher) 适用的场景是, 协程不占用 CPU 时间,
 也不更新那些限定于某个特定线程的共享数据(比如 UI).

**(4) Dispatchers.IO**
 用于执行 IO 类型的协程。它会和 Dispatchers.Default 共享线程池。
 所以在一个 Dispatchers.Default 的作用域中，试图切换到 Dispatchers.IO 中执行，线程不一定会切换。例如：

除了继承了 上下文(CoroutineContext) 的调度器，还有几个常见的 CoroutineContext 子类：

### 3.2 EmptyCoroutineContext

空上下文。
 launch等协程构造器使用的默认上下文就是这个。
 当使用这个对象时，表示当前的 block 执行在父协程的上下文。

例如：

```kotlin
fun main() {
    runBlocking(context = ctx) {
    
        // 这里没有指定上下文，默认使用 EmptyCoroutineContext，
        // 也就是使用 runBlocking 的上下文 ctx
        GlobalScope.launch {}
    }
}
```

如果没有父协程，会新创建一个线程池，例如：

```kotlin
fun main () {

    // 外层没有父协程，且使用 EmptyCoroutineContext，
    // 则内部会新建一个线程池
    GlobalScope.launch {}
}
```

### 3.3 newSingleThreadContext

当使用这个参数时，表示当前 block 执行在新的协程上下文中，例如：

```kotlin
fun main () {
    
    val ctx = newSingleThreadContext("新的协程")
    GlobalScope.launch(context = ctx) {
        log("在新协程中执行")
        // 不再使用时，需要手动 close 掉，节省线程资源
        ctx.close()
    }
}
```

注意：我们手动创建的协程上下文，一定要在不用时 close 掉。