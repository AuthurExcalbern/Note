# Kotlin 调用 Java示例

Kotlin 很像 Java。它长得不像 Clojure 或者 Scala 那么奇怪（承认现实把，这两种语言就是挺奇怪的）。所以我们学 Kotlin 应该很快。这门语言显然就是写给 Java 开发者来用的。

Kotlin 在设计之初就考虑了与 Java 的互操作性。我们可以从 Kotlin 中自然地调用现存的 Java 代码。例如，下面是一个Kotlin调用Java中的Okhttp库的代码：

```kotlin
package com.easy.kotlin

import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object OkhttpUtils {
    fun get(url: String): String? {
        var result: String? = ""
        val okhttp = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.HOURS)
                .readTimeout(1, TimeUnit.HOURS)
                .writeTimeout(1, TimeUnit.HOURS)
                .build()

        val request = Request.Builder()
                .url(url)
                .build()

        val call = okhttp.newCall(request)

        try {
            val response = call.execute()
            result = response.body()?.string()
            val f = File("run.log")
            f.appendText(result!!)
            f.appendText("\n")

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }
}
```

Kotlin调用Java代码跟Groovy一样流畅自如（但是不像Groovy那样“怎么写都对，但是一运行就报错”，因为Groovy是一门动态类型语言，而Kotlin则是一门强类型的静态类型语言）。我们基本不需要改变什么就可以直接使用Java中的API库。

并且在 Java 代码中也可以很顺利地调用 Kotlin 代码：

```tsx
package com.easy.kotlin;

import com.alibaba.fastjson.JSON;

/**
 * Created by jack on 2017/7/14.
 */
public class JSONUtils {
    public static String toJsonString(Object o) {
        return JSON.toJSONString(o);
    }

    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        String result = OkhttpUtils.INSTANCE.get(url);
        System.out.println(result);
    }
}
```

因为Kotlin跟Java本是两门语言，所以在互相调用的时候，会有一些特殊的语法。这里的使用Java调用Kotlin的object对象函数的语法就是`OkhttpUtils.INSTANCE.get(url)`, 我们看到**这里多了个INSTANCE** 。

我们甚至也可以在一个项目中同时使用Kotlin和Java两 种语言混合编程。我们可以在下一章中看到，我们在一个SpringBoot工程中同时使用了Kotlin和Java两种语言进行混合开发。

下面我们来继续介绍 Kotlin 调用 Java 代码的一些细节。

## Kotlin使用Java的集合类

Kotlin的集合类API很多就是直接使用的Java的API来实现的。我们在使用的时候，毫无违和感，自然天成：

```kotlin
@RunWith(JUnit4::class)
class KotlinUsingJavaTest {
    @Test fun testArrayList() {
        val source = listOf<Int>(1, 2, 3, 4, 5)
        // 使用Java的ArrayList
        val list = ArrayList<Int>()
        for (item in source) {
            list.add(item) // ArrayList.add()
        }
        for (i in 0..source.size - 1) {
            list[i] = source[i] // 调用 get 和 set
        }
    }
}
```

## Kotlin调用Java中的Getter 和 Setter

在Java中遵循这样的约定： getter 方法无参数并以 `get` 开头，setter 方法单参数并以 `set` 开头。在 Kotlin 中我们可以直接表示为属性。 例如，我们写一个带setter和getter的Java类：

```kotlin
package com.easy.kotlin;

import java.util.Date;

/**
 * Created by jack on 2017/7/14.
 */
public class Product {
    Long id;
    String name;
    String category;
    Date gmtCreated;
    Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
```

然后，我们在Kotlin可以直接使用属性名字进行get和set操作：

```kotlin
@RunWith(JUnit4::class)
class ProductTest {
    @Test fun testGetterSetter() {
        val product = Product()
        product.name = "账务系统"
        product.category = "金融财务类"
        product.gmtCreated = Date()
        product.gmtModified = Date()
        println(JSONUtils.toJsonString(product))
        Assert.assertTrue(product.getName() == "账务系统")
        Assert.assertTrue(product.name == "账务系统")
        Assert.assertTrue(product.getCategory() == "金融财务类")
        Assert.assertTrue(product.category == "金融财务类")
    }
}
```

当然，我们还可以像在Java中一样，直接调用像product.getName()、product.setName("Kotlin")这样的getter、setter方法。

## 调用Java中返回 void 的方法

如果一个 Java 方法返回 void，那么从 Kotlin 调用时中返回 `Unit`。

```java
public class Admin {
    String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "name='" + name + '\'' +
            '}';
    }
}
```

我们这样调用

```go
val setReturn = admin.setName("root")
println(setReturn)
```

将输出：kotlin.Unit

## 空安全和平台类型

我们知道Java 中的任何引用都可能是null，这样我们在使用 Kotlin调用来自 Java 的对象的时候就有可能会出现空安全的问题。

Java 声明的类型在 Kotlin 中会被特别对待并称为***平台类型***（platform types ）。对这种类型的空检查会放宽，因此它们的安全保证与在 Java 中相同。

请看以下示例：

```kotlin
@RunWith(JUnit4::class)
class CallingJavaNullSafe {
    @Test fun testCallingJavaNullSafe() {
        val product = Product()
        // product.name = null
        product.category = "金融财务类"
        product.gmtCreated = Date()
        product.gmtModified = Date()
        println(JSONUtils.toJsonString(product))

        val name = product.name
        println("product name is ${name}")

        val eqName = name == "账务系统"
        println(eqName)

        name.substring(1)
    }
}
```

上面的代码可以正确编译通过。Kotlin编译器对来自Java的空值name（平台类型）放宽了空检查`name.substring(1)`。但是这样的空指针异常仍然会在运行时抛出来。

运行上面的代码，我们可以看到输出：

```dart
{"category":"金融财务类","gmtCreated":1500050426817,"gmtModified":1500050426817}
product name is null
false

null cannot be cast to non-null type java.lang.String
kotlin.TypeCastException: null cannot be cast to non-null type java.lang.String
    at com.easy.kotlin.CallingJavaNullSafe.testCallingJavaNullSafe(CallingJavaNullSafe.kt:27)
```

我们没有设置name的值，在Java它就是null。我们在Kotlin代码中使用了这个name进行计算，我们可以看出：

```go
        val eqName = name == "账务系统"
        println(eqName)
```

可以正确输出false。这表明Kotlin的判断字符串是否相等已经对null的情况作了判断处理，这样的代码如果在Java中调用 `name.equals("账务系统")` 就该抛空指针异常了。

但是当我们直接使用name这个值来调用`name.substring(1)`的时候，Kotlin编译器不会检查这个空异常，但是运行时还是要报错的：`null cannot be cast to non-null type java.lang.String`。

如果我们不想看到这样的异常，而是当name是null的时候，安静的输出null，直接使用Kotlin中的空安全的调用 `.?`  ：

```css
name?.substring(1)
```

这样，运行的时候不会抛出异常，直接安静的返回null。

## 平台类型

平台类型不能在程序中显式表述，因此在语言中没有相应语法。 然而，编译器和 IDE 有时需要（在错误信息中、参数信息中等）显示他们，所以我们用一个助记符来表示他们：

- `T!`  : 表示 `T` 或者 `T?`
- `(Mutable) Collection<T>!`  :  表示  “可以可变或不可变、可空或不可空的 T 的 Java 集合”
- `Array<(out) T>!`  :  表示“可空或者不可空的 T（或 T 的子类型）的 Java 数组”

## Kotlin与Java中的类型映射

Kotlin 特殊处理一部分 Java 类型。这样的类型不是“按原样”从 Java 加载，而是 *映射* 到相应的 Kotlin 类型。

映射只发生在编译期间，运行时表示保持不变。

Java 的原生类型映射到相应的 Kotlin 类型：

| **Java 类型** | **Kotlin 类型**  |
| ------------- | ---------------- |
| `byte`        | `kotlin.Byte`    |
| `short`       | `kotlin.Short`   |
| `int`         | `kotlin.Int`     |
| `long`        | `kotlin.Long`    |
| `char`        | `kotlin.Char`    |
| `float`       | `kotlin.Float`   |
| `double`      | `kotlin.Double`  |
| `boolean`     | `kotlin.Boolean` |

Java中的一些内置类型也会作相应的映射：

| **Java 类型**            | **Kotlin 类型**        |
| ------------------------ | ---------------------- |
| `java.lang.Object`       | `kotlin.Any!`          |
| `java.lang.Cloneable`    | `kotlin.Cloneable!`    |
| `java.lang.Comparable`   | `kotlin.Comparable!`   |
| `java.lang.Enum`         | `kotlin.Enum!`         |
| `java.lang.Annotation`   | `kotlin.Annotation!`   |
| `java.lang.Deprecated`   | `kotlin.Deprecated!`   |
| `java.lang.CharSequence` | `kotlin.CharSequence!` |
| `java.lang.String`       | `kotlin.String!`       |
| `java.lang.Number`       | `kotlin.Number!`       |
| `java.lang.Throwable`    | `kotlin.Throwable!`    |

Java 的装箱原始类型映射到对应的可空Kotlin 类型：

| **Java 类型**         | **Kotlin 类型**   |
| --------------------- | ----------------- |
| `java.lang.Byte`      | `kotlin.Byte?`    |
| `java.lang.Short`     | `kotlin.Short?`   |
| `java.lang.Integer`   | `kotlin.Int?`     |
| `java.lang.Long`      | `kotlin.Long?`    |
| `java.lang.Character` | `kotlin.Char?`    |
| `java.lang.Float`     | `kotlin.Float?`   |
| `java.lang.Double`    | `kotlin.Double?`  |
| `java.lang.Boolean`   | `kotlin.Boolean?` |

另外，用作类型参数的Java类型映射到Kotlin中的平台类型：
 例如，`List<java.lang.Integer>` 在 Kotlin 中会成为 `List<Int!>`。

集合类型在 Kotlin 中可以是只读的或可变的，因此 Java 集合类型作如下映射：
 （下表中的所有 Kotlin 类型都在 `kotlin.collections`包中）:

| **Java 类型**     | **Kotlin 只读类型** | **Kotlin 可变类型**            | **加载的平台类型**                   |
| ----------------- | ------------------- | ------------------------------ | ------------------------------------ |
| `Iterator<T>`     | `Iterator<T>`       | `MutableIterator<T>`           | `(Mutable)Iterator<T>!`              |
| `Iterable<T>`     | `Iterable<T>`       | `MutableIterable<T>`           | `(Mutable)Iterable<T>!`              |
| `Collection<T>`   | `Collection<T>`     | `MutableCollection<T>`         | `(Mutable)Collection<T>!`            |
| `Set<T>`          | `Set<T>`            | `MutableSet<T>`                | `(Mutable)Set<T>!`                   |
| `List<T>`         | `List<T>`           | `MutableList<T>`               | `(Mutable)List<T>!`                  |
| `ListIterator<T>` | `ListIterator<T>`   | `MutableListIterator<T>`       | `(Mutable)ListIterator<T>!`          |
| `Map<K, V>`       | `Map<K, V>`         | `MutableMap<K, V>`             | `(Mutable)Map<K, V>!`                |
| `Map.Entry<K, V>` | `Map.Entry<K, V>`   | `MutableMap.MutableEntry<K,V>` | `(Mutable)Map.(Mutable)Entry<K, V>!` |

Java 的数组映射：

| **Java 类型** | **Kotlin 类型**               |
| ------------- | ----------------------------- |
| `int[]`       | `kotlin.IntArray!`            |
| `String[]`    | `kotlin.Array<(out) String>!` |

## Kotlin 中使用 Java 的泛型

Kotlin 的泛型与 Java 有点不同。当将 Java 类型导入 Kotlin 时，我们会执行一些转换：

| Kotlin 的泛型      | Java 的泛型          | 说明                        |
| ------------------ | -------------------- | --------------------------- |
| `Foo<out Bar!>!`   | `Foo<? extends Bar>` | Java 的通配符转换成类型投影 |
| `Foo<? super Bar>` | `Foo<in Bar!>!`      | 同上                        |
| `List<*>!`         | `List`               | Java的原始类型转换成星投影  |

和 Java 一样，Kotlin 在运行时不保留泛型，即对象不携带传递到他们构造器中的那些类型参数的实际类型。

即 `ArrayList<Integer>()` 和 `ArrayList<Character>()` 是不能区分的。

## Kotlin与Java 中的数组

与 Java 不同，Kotlin 中的数组是非型变的，即 Kotlin 不允许我们把一个 `Array<String>` 赋值给一个 `Array<Any>`。

Java 平台上，持有原生数据类型的数组避免了装箱/拆箱操作的开销。
 在Kotlin中，对于每种原生类型的数组都有一个特化的类（`IntArray`、 `DoubleArray`、 `CharArray` 等）来实现同样的功能。它们与 `Array` 类无关，并且会编译成 Java 原生类型数组以获得最佳性能。

## Java 可变参数

Java 类有时声明一个具有可变数量参数（varargs）的方法来使用索引。

```java
public class VarArgsDemo<T> {
    static VarArgsDemo vad = new VarArgsDemo();

    public static void main(String... agrs) {
        System.out.println(vad.append("a", "b", "c"));
        System.out.println(vad.append(1, 2, 3));
        System.out.println(vad.append(1, 2, "3"));
    }

    public String append(T... element) {
        StringBuilder result = new StringBuilder();
        for (T e : element) {
            result.append(e);
        }
        return result.toString();
    }
}
```

在Kotlin中，我们使用展开运算符 `*` 来传递这个varargs：

```kotlin
@RunWith(JUnit4::class)
class VarArgsDemoTest {
    @Test fun testVarArgsDemo() {
        val varArgsDemo = VarArgsDemo<Any?>()
        val array = arrayOf(0, 1, 2, 3)
        val result = varArgsDemo.append(*array)
        println(result)
    }
}
```

运行输出：0123

## 非受检异常

在 Kotlin 中，所有异常都是非受检的（Non-Checked Exceptions），这意味着编译器不会强迫你捕获其中的任何一个。而在Java中会要求我们捕获异常，例如下面的代码：

![](./img/非受检异常.webp)

也就是说，我们需要写类似下面的`try catch`代码块：

```php
        try {
            jsonUtils.parseObject("{}");
        } catch (Exception e) {
            e.printStackTrace();
        }
```

然而在Kotlin中情况就不是这样子了：当我们调用一个声明受检异常的 Java 方法时，Kotlin 不会强迫你做任何事情：

```kotlin
    @Test fun testNonCheckedExceptions() {
        val jsonUtils = JSONUtils()
        jsonUtils.parseObject("{}")
    }
```

但是，我们在运行的时候，还是会抛异常：

```css
com.easy.kotlin.CallingJavaNullSafe > testNonCheckedExceptions FAILED
    java.lang.Exception at CallingJavaNullSafe.kt:34
```

Kotlin的不受检异常，这样也会导致运行时抛出异常。关于异常的处理，该处理的终归还是要处理的。

## 对象方法

Java中的java.lang.Object定义如下：

```java
public class Object {
    private static native void registerNatives();
    static {
        registerNatives();
    }
    public final native Class<?> getClass();
    public native int hashCode();
    public boolean equals(Object obj) {
        return (this == obj);
    }
    protected native Object clone() throws CloneNotSupportedException;
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
    public final native void notify();
    public final native void notifyAll();
    public final native void wait(long timeout) throws InterruptedException;
    public final void wait(long timeout, int nanos) throws InterruptedException {...}
    public final void wait() throws InterruptedException {
        wait(0);
    }
    protected void finalize() throws Throwable { }
```

当 Java 类型导入到 Kotlin 中时，类型 `java.lang.Object` 的所有引用都成了 `Any`。`Any`只声明了 `toString()`、`hashCode()` 和 `equals()` 函数。怎样才能用到 `java.lang.Object` 的其他成员方法呢？下面我们来看下。

### wait()/notify()

《Effective Java》 第 69 条中建议优先使用并发工具（concurrency utilities）而不是 `wait()` 和 `notify()`。因此，类型 `Any` 的引用没有提供这两个方法。

如果我们真的需要调用它们的话，可以将其转换为 `java.lang.Object`来使用：

```kotlin
(foo as java.lang.Object).wait()
```

### getClass()

要取得对象的 Java 类，我们可以在类引用上使用 `java` 扩展属性，它是Kotlin的反射类kotlin.reflect.KClass的扩展属性。

```kotlin
val fooClass = foo::class.java
```

上面的代码使用了自 Kotlin 1.1 起支持的绑定类引用。我们也可以使用 `javaClass` 扩展属性。

```kotlin
val fooClass = foo.javaClass
```

### clone()

要覆盖 `clone()`，需要继承 `kotlin.Cloneable`：

```kotlin
class Example : Cloneable {
    override fun clone(): Any { …… }
}
```

要谨慎地改写clone方法。

### finalize()

要覆盖 `finalize()`，我们只需要声明它即可，不用再写 *override*关键字：

```kotlin
class C {
    protected fun finalize() {
        // 终止化逻辑
    }
}
```

## 访问静态成员

Java 类的静态成员会形成该类的“伴生对象”。我们可以直接显式访问其成员。例如：

一个带静态方法的Java类

```kotlin
public class JSONUtils {
    public static String toJsonString(Object o) {
        return JSON.toJSONString(o);
    }
}
```

我们在Kotlin代码可以直接这样调用:

```kotlin
@RunWith(JUnit4::class)
class JSONUtilsTest {
    @Test fun testJSONUtils() {
        val userService = UserServiceImpl()
        val user = userService.findByName("admin")
        Assert.assertTrue(user.name == "admin")

        val userJson = JSONUtils.toJsonString(user)
        println(userJson)
        Assert.assertTrue(userJson == "{\"name\":\"admin\",\"password\":\"admin\"}")
    }
}
```

上面我们提到过，如果是反过来调用，Java调用Kotlin中的object对象类中的函数，需要使用object的  `对象名.INSTANCE` 来调用函数。

## Kotlin与Java 的反射

我们可以使用 `instance::class.java`、`ClassName::class.java` 或者 `instance.javaClass` 通过 `java.lang.Class` 来进入 Java 的反射类`java.lang.Class`， 之后我们就可以使用Java中的反射的功能特性了。

代码示例：

```kotlin
@RunWith(JUnit4::class)
class RefectClassTest {
    @Test fun testGetterSetter() {
        val product = Product()
        val pClz = product::class.java
        println(pClz.canonicalName)
        pClz.declaredFields.forEach { println(it) }
        pClz.declaredMethods.forEach {
            println(it.name);
            it.parameters.forEach { println(it) }
        }
    }
}
```

运行上面的代码输出：

```css
com.easy.kotlin.Product
java.lang.Long com.easy.kotlin.Product.id
java.lang.String com.easy.kotlin.Product.name
java.lang.String com.easy.kotlin.Product.category
java.util.Date com.easy.kotlin.Product.gmtCreated
java.util.Date com.easy.kotlin.Product.gmtModified
getName
setName
java.lang.String arg0
getId
setId
java.lang.Long arg0
setCategory
java.lang.String arg0
getGmtCreated
setGmtCreated
java.util.Date arg0
getGmtModified
setGmtModified
java.util.Date arg0
getCategory
```

## SAM 转换

我们在Kotlin中，要某个函数做某件事时，会传一个函数参数给它。 而在Java中，并不支持传送函数参数。通常Java的实现方式是将动作放在一个实现某接口的类中，然后将该类的一个实例传递给另一个方法。在大多数情况下，这些接口只有单个抽象方法（single abstract method），在Java中被称为SAM类型。

例如：Runnable接口：

```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

在 Java 8中我们也通常称之为函数式接口。

Kotlin 支持 SAM 转换。Kotlin 的函数字面值可以被自动的转换成只有一个非默认方法的 Java 接口的实现，只要这个方法的参数类型能够与这个 Kotlin 函数的参数类型相匹配。

我们可以这样创建 SAM 接口的实例：

```kotlin
val runnable = Runnable { println("执行测试") } // Kotlin 调用Java的SAM接口方法
```

测试代码：

```kotlin
@RunWith(JUnit4::class)
class SAMFunctionalInterfaceTest {
    @Test fun testSAMFunctionalInterface() {
        val runnable = Runnable { println("执行测试") }
        val thread = Thread(runnable)
        thread.start()
    }
}
```

要注意的是，SAM 转换只适用于接口，而不适用于抽象类，即使这些抽象类也只有一个抽象方法。

还要注意，此功能只适用于 Java 互操作；因为 Kotlin 具有合适的函数类型，所以不需要将函数自动转换为 Kotlin 接口的实现。

## Java使用了Kotlin的关键字

一些 Kotlin 关键字在 Java 中是有效标识符：*in*、 *object*、 *is*等等。

如果一个 Java 库使用了 Kotlin 关键字作为方法，我们可以通过反引号（`）字符转义它来调用该方法。例如我们有个Java类，其中有个is方法：

```java
public class MathTools {

    public boolean is(Object o) {
        return true;
    }

}
```

那么我们在Kotlin代码这样调用这个is方法：

```kotlin
@RunWith(JUnit4::class)
class MathToolsTest {
    @Test fun testISKeyWord(){
        val b = MathTools().`is`(1)
    }
}
```

# Java 调用 Kotlin

Java 同样也可以调用 Kotlin 代码。但是要多用一些注解语法。

## Java访问Kotlin属性

Kotlin 属性会编译成以下 Java 元素：

- 一个 getter 方法，名称通过加前缀 `get` 算出；
- 一个 setter 方法，名称通过加前缀 `set` 算出（只适用于 `var` 属性）；
- 一个与属性名称相同的私有字段。

例如，下面的Kotlin类：

```dart
class Department {
    var id: Long = -1L
    var name: String = "Dept"
}
```

会被编译成对应的 Java 代码：

```java
public final class Department {
   private long id = -1L;
   @NotNull
   private String name = "Dept";

   public final long getId() {
      return this.id;
   }

   public final void setId(long var1) {
      this.id = var1;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.name = var1;
   }
}
```

我们可以看出，在Kotlin中的Long类型被编译成Java中的原生的long了。
 我们在Java代码这样调用：

```java
@RunWith(JUnit4.class)
public class JavaCallingKotlinCodeTest {
    @Test
    public void testProperty() {
        Department d = new Department();
        d.setId(1);
        d.setName("技术部");

        Assert.assertTrue(1 == d.getId());
        Assert.assertTrue("技术部".equals(d.getName()));

    }
}
```

另外，如果Kotlin的属性名以 `is` 开头，则使用不同的名称映射规则：

- getter 的名称直接使用属性名称
- setter 的名称是通过将 `is` 替换为 `set` 获得。

例如，对于属性 `isOpen`，其 getter 会称做 `isOpen()`，而其 setter 会称做 `setOpen()`。

这一规则适用于任何类型的属性，并不仅限于 `Boolean`。

代码示例：

Kotlin代码

```jsx
class Department {
    var id: Long = -1L
    var name: String = "Dept"
    var isOpen:Boolean = true
    var isBig:String = "Y"
}
```

Java调用Kotlin的测试代码：

```csharp
    @Test
    public void testProperty() {
        Department d = new Department();
        d.setId(1);
        d.setName("技术部");
        d.setBig("Y");
        d.setOpen(true);

        Assert.assertTrue(1 == d.getId());
        Assert.assertTrue("技术部".equals(d.getName()));
        Assert.assertTrue("Y".equals(d.isBig()));
        Assert.assertTrue(d.isOpen());

    }
```

## Java调用Kotlin的包级函数

在 `package com.easy.kotlin` 包内的 `KotlinExample.kt` 源文件中声明的所有的函数和属性，包括扩展函数，都将编译成一个名为 `com.easy.kotlin.KotlinExampleKt` 的 Java 类中的静态方法。

代码示例：

Kotlin的包级属性、函数代码：

```kotlin
package com.easy.kotlin

/**
 * Created by jack on 2017/7/15.
 */

fun f1() {
    println("I am f1")
}

fun f2() {
    println("I am f2")
}

val p: String = "PPP"

fun String.swap(index1: Int, index2: Int): String {
    val strArray = this.toCharArray()
    val tmp = strArray[index1]
    strArray[index1] = strArray[index2]
    strArray[index2] = tmp

    var result = ""
    strArray.forEach { result += it }
    return result
}

fun main(args: Array<String>) {
    println("abc".swap(0, 2))
}
```

编译成对应的Java的代码：

```java
public final class KotlinExampleKt {
   @NotNull
   private static final String p = "PPP";

   public static final void f1() {
      String var0 = "I am f1";
      System.out.println(var0);
   }

   public static final void f2() {
      String var0 = "I am f2";
      System.out.println(var0);
   }

   @NotNull
   public static final String getP() {
      return p;
   }

   @NotNull
   public static final String swap(@NotNull String $receiver, int index1, int index2) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      char[] var10000 = $receiver.toCharArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
      char[] strArray = var10000;
      char tmp = strArray[index1];
      strArray[index1] = strArray[index2];
      strArray[index2] = tmp;
      Object result = "";
      char[] $receiver$iv = strArray;

      for(int var7 = 0; var7 < $receiver$iv.length; ++var7) {
         char element$iv = $receiver$iv[var7];
         result = result + element$iv;
      }

      return result;
   }

   public static final void main(@NotNull String[] args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      String var1 = swap("abc", 0, 2);
      System.out.println(var1);
   }
}
```

我们可以看到，Kotlin中的扩展函数

```kotlin
fun String.swap(index1: Int, index2: Int): String
```

被编译成

```dart
public static final String swap(@NotNull String $receiver, int index1, int index2)
```

Kotlin中的`String.` 接收者被当做Java方法中的第一个参数传入。

Java调用Kotlin包级属性、函数的测试代码：

```css
    @Test
    public void testPackageFun() {
        KotlinExampleKt.f1();
        KotlinExampleKt.f2();
        System.out.println(KotlinExampleKt.getP());
        KotlinExampleKt.swap("abc",0,1);
    }
```

运行输出：

```undefined
I am f1
I am f2
PPP
bac
```

另外，要注意的这里生成的类KotlinExampleKt，我们不能使用new来创建实例对象：

```cpp
KotlinExampleKt example = new KotlinExampleKt();// 报错
```

报如下错误：

```kotlin
error: cannot find symbol
        KotlinExampleKt example = new KotlinExampleKt();
                                  ^
  symbol:   constructor KotlinExampleKt()
  location: class KotlinExampleKt
1 error
```

在编程中，我们推荐使用Kotlin默认的命名生成规则。如果确实有特殊场景需要自定义Kotlin包级函数对应的生成Java类的名字，我们可以使用 `@JvmName` 注解修改生成的 Java 类的类名：

```kotlin
@file:JvmName("MyKotlinExample")

package com.easy.kotlin

/**
 * Created by jack on 2017/7/15.
 */

fun f3() {
    println("I am f3")
}

fun f4() {
    println("I am f4")
}

val p2: String = "PPP"
```

测试代码：

```java
MyKotlinExample.f3();
MyKotlinExample.f4();
```

## 实例字段

我们使用 `@JvmField` 注解对Kotlin中的属性字段标注，表示这是一个实例字段（Instance Fields），Kotlin编译器在处理的时候，将不会给这个字段生成getters/setters方法。

```kotlin
class Department {
    var id: Long = -1L
    var name: String = "Dept"
    var isOpen: Boolean = true
    var isBig: String = "Y"

    @JvmField var NO = 0
}
```

映射成Java的代码就是：

```java
public final class Department {
   private long id = -1L;
   @NotNull
   private String name = "Dept";
   private boolean isOpen = true;
   @NotNull
   private String isBig = "Y";
   @JvmField
   public int NO;

   public final long getId() {
      return this.id;
   }

   public final void setId(long var1) {
      this.id = var1;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.name = var1;
   }

   public final boolean isOpen() {
      return this.isOpen;
   }

   public final void setOpen(boolean var1) {
      this.isOpen = var1;
   }

   @NotNull
   public final String isBig() {
      return this.isBig;
   }

   public final void setBig(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.isBig = var1;
   }
}
```

我们在Java中调用的时候，就直接使用这个属性实例字段`NO` ：

```csharp
System.out.println(d.NO = 10);
```

## 静态字段

Kotlin中在命名对象或伴生对象中声明的 属性:

```kotlin
class Department {
    ...
    companion object {
        var innerID = "X001"
        @JvmField
        var innerName = "DEP"
    }
}
```

innerID、innerName这两个字段的区别在于可见性上：

```tsx
   @NotNull
   private static String innerID = "X001";
   @JvmField
   @NotNull
   public static String innerName = "DEP";
```

这个私有的innerID通过Companion对象来封装，提供出public的getInnerID() 、setInnerID来访问：

```java
   public static final class Companion {
      @NotNull
      public final String getInnerID() {
         return Department.innerID;
      }

      public final void setInnerID(@NotNull String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         Department.innerID = var1;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
```

我们在Java访问的`innerID`时候，是通过Companion来访问：

```css
Department.Companion.getInnerID()
```

而我们使用`@JvmField`注解的字段`innerName` ，Kotlin编译器会把它的访问权限设置是public的，这样我们就可以这样访问这个属性字段了：

```css
Department.innerName
```

## 静态方法

Kotlin 中，我还可以将命名对象或伴生对象中定义的函数标注为 `@JvmStatic`，这样编译器既会在相应对象的类中生成静态方法，也会在对象自身中生成实例方法。

跟静态属性类似的，我们看下面的代码示例：

```kotlin
class Department {
    ...
    companion object {
        var innerID = "X001"
        @JvmField
        var innerName = "DEP"

        fun getObjectName() = "ONAME"
        @JvmStatic
        fun getObjectID() = "OID"
    }
}
```

编译器编译之后，反编译成的对应的Java代码：

```java
public final class Department {
   ...
   @JvmStatic
   @NotNull
   public static final String getObjectID() {
      return Companion.getObjectID();
   }

   public static final class Companion {
      ...
      @NotNull
      public final String getObjectName() {
         return "ONAME";
      }

      @JvmStatic
      @NotNull
      public final String getObjectID() {
         return "OID";
      }
     ...
   }
}
```

在Java中调用的代码如下：

```cpp
        Department.Companion.getObjectID(); // OK
        Department.Companion.getObjectName(); // OK, 唯一的工作方式
        Department.getObjectID(); // ALSO OK
        Department.getObjectName(); // ERROR
```

这些注解语法是编译器为了更加方便Java调用Kotlin代码提供的一些简便技巧。这样可使得Java中调用Kotlin代码更加自然优雅些。

## 可见性

Kotlin 的可见性与Java的可见性的映射关系如下表所示：

| Kotlin中的声明 | Java中的声明 |
| -------------- | ------------ |
| `private`      | `private`    |
| `protected`    | `protected`  |
| `internal`     | `public`     |
| `public`       | `public`     |

例如下面的Kotlin代码：

```kotlin
class ProgrammingBook {
    private var isbn: String = "978-7-111-44250-9"
    protected var author: String = "Cay"
    public var name: String = "Core Java"
    internal var pages: Int = 300

    private fun findISBN(): String = "978-7-111-44250-9"
    protected fun findAuthor(): String = "Cay"
    public fun findName(): String = "Core Java"
    internal fun findPages(): Int = 300
}
```

对应的Java的代码是：

```kotlin
public final class ProgrammingBook {
   private String isbn = "978-7-111-44250-9";
   @NotNull
   private String author = "Cay";
   @NotNull
   private String name = "Core Java";
   private int pages = 300;

   @NotNull
   protected final String getAuthor() {
      return this.author;
   }

   protected final void setAuthor(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.author = var1;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.name = var1;
   }

   public final int getPages$production_sources_for_module_chapter10_interoperability_main() {
      return this.pages;
   }

   public final void setPages$production_sources_for_module_chapter10_interoperability_main(int var1) {
      this.pages = var1;
   }

   private final String findISBN() {
      return "978-7-111-44250-9";
   }

   @NotNull
   protected final String findAuthor() {
      return "Cay";
   }

   @NotNull
   public final String findName() {
      return "Core Java";
   }

   public final int findPages$production_sources_for_module_chapter10_interoperability_main() {
      return 300;
   }
}
```

我们可以看到Kotlin中的可见性跟Java中的基本相同。

## 生成默认参数值函数的重载

我们在Kotlin中写一个有默认参数值的 Kotlin 方法，它会对每一个有默认值的参数都生成一个重载函数。这样的Kotlin函数，在 Java 中调用的话，只会有一个所有参数都存在的完整参数签名方法可见。如果我们希望Java像Kotlin中一样可以调用多个重载，可以使用`@JvmOverloads`注解。

下面我们来通过一个实例对比两者的区别：

这是一段Kotlin代码：

```kotlin
class OverridesFunWithDefaultParams {
    fun f1(a: Int = 0, b: String = "B") {

    }

    @JvmOverloads fun f2(a: Int = 0, b: String = "B") {

    }
}
```

函数f1 和 f2 都带有默认参数。测试代码如下：

```java
    @Test
    public void testOverridesFunWithDefaultParams() {
        OverridesFunWithDefaultParams ofdp = new OverridesFunWithDefaultParams();
        ofdp.f1(1, "a");
        ofdp.f2();
        ofdp.f2(2);
        ofdp.f2(2, "b");
    }
```

这就是`@JvmOverloads`注解的作用，编译器会处理这个注解所标注的函数，并为之生成额外的重载函数给Java调用。

## 检查Kotlin中异常

如上所述，Kotlin 没有受检异常。即像下面像这样的 Kotlin 函数：

```kotlin
class CheckKotlinException {
    fun thisIsAFunWithException() {
        throw Exception("I am an exception in kotlin")
    }
}
```

在Java中调用，编译器是不会检查这个异常的：

```java
    @Test
    public void testCheckKotlinException() {
        CheckKotlinException cke = new CheckKotlinException();
        cke.thisIsAFunWithException();// Java编译器不检查这个Kotlin中的异常
    }
```

当然，在运行时，这个异常还是会抛出来。然后，如果我们想要在 Java 中调用它并捕捉这个异常，我们可以给Kotlin中的函数加上注解`@Throws(Exception::class)`, 就像下面这样：

```kotlin
    @Throws(Exception::class)
    fun thisIsAnotherFunWithException() {
        throw Exception("I am Another exception in kotlin")
    }
```

然后，我们在Java中调用的时候，Java编译器就会检查这个异常：

![](./img/检查异常.webp)

最后，我们的代码就需要捕获该异常并处理它。

完整的示例代码如下：

```kotlin
package com.easy.kotlin

/**
 * Created by jack on 2017/7/15.
 */
class CheckKotlinException {
    fun thisIsAFunWithException() {
        throw Exception("I am an exception in kotlin")
    }

    @Throws(Exception::class)
    fun thisIsAnotherFunWithException() {
        throw Exception("I am Another exception in kotlin")
    }
}
```

测试代码：

```java
package com.easy.kotlin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by jack on 2017/7/15.
 */
@RunWith(JUnit4.class)
public class CheckKotlinExceptionTest {
    @Test
    public void testCheckKotlinException() {
        CheckKotlinException cke = new CheckKotlinException();
        cke.thisIsAFunWithException();// Java编译器不检查这个Kotlin中的异常

        // Kotlin中显示声明了异常，Java编译器会检查这个异常
        // cke.thisIsAnotherFunWithException();
        try {
            cke.thisIsAnotherFunWithException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Nothing 类型

在Kotlin中`Nothing`类型是一个特殊的类型，它在 Java 中没有的对应的类型。在使用 `Nothing` 参数的地方会生成一个原始类型。

例如下面的Kotlin代码：

```kotlin
fun emptyList(): List<Nothing> = listOf()
```

对应到Java代码中是：

```java
   @NotNull
   public final List emptyList() {
      return CollectionsKt.emptyList();
   }
```

Kotlin中的`List<Nothing>` 映射为原生类型`List` 。

