## Kotlin 对比 Java 的简化语法

### 语句与表达式

在Kotlin 中， if 是表达式，而不是语句。语句和表达式的区别在于，表达式有值，并且能作为另一个表达式的一部分使用；而语句总是包围着它的代码块中的顶层元素，并且没有自己的值。

**在Java 中，所有的控制结构都是语句。而在Kotlin 中，除了循环（ for, do 和do/while ）以外大多数控制结构都是表达式。**

```kotlin
//表达式函数体
fun max(a : Int , b: Int) : Int = if (a > b) a else b
```

### 更简单的字符串格式化： 字符串模板

```kotlin
//可以在 {} 中使用 ""
fun main(args: Array<String>){
    println("Hello, ${if (args.size > 0) args[0] else "someone"} !")
}
```

### 智能转化

在Kotlin 中，编译器帮你完成了这些工作。如果你检查过一个变量是某种类型，后面就不再需要转换它，可以就把它当作你检查过的类型使用。事实上编译器为你执行了类型转换，我们把这种行为称为智能转换。

```kotlin
//显示转化
val n = e as Num

if (e is Sum) {//true：e 自动转化为 Sum 类型，仅当 e 不再发生变化时有效
    return eval(e.right) + eval(e.left)
}
```

### when 对比 switch

- Java 的 switch 要求必须使用常量（枚举常量、字符串或者数字字面值）作为分支条件
- when 允许使用任何对象

```kotlin
fun mix(c1: Color, c2: Color) = //when 作为表达式返回
    when(setOf (cl, c2)) {
        setOf(RED, YELLOW) ->ORANGE
        setOf(YELLOW, BLUE) ->GREEN
        setOf(BLUE , VIOLET) ->INDIGO
        else -> throw Exception("Dirty color")
    }
```

### 消除静态工具类：顶层函数和属性

Java 作为一门面向对象的语言，需要所有的代码都写作类的函数。有时存在一个基本的对象，但你不想通过实例函数来添加操作，让它的API 继续膨胀。结果就是，最终这些类将不包含任何的状态或者实例函数，而是仅仅作为一堆静态函数的容器。

**Kotlin 可以把这些函数直接放到代码文件的顶层，不用从属于任何的类**。这些放在文件顶层的函数依然是包内的成员，如果你需要从包外访问它，则需要import， 但不再需要额外包一层。

```java
//Java 为了声明静态函数，需要有作为容器的类
package strings;
public class JoinKt{
    public static String joinToString(...) { ... }
}
```

```kotlin
//声明joinToString(...)作为顶层函数
package strings
fun joinToString(...): String { ... }
```

```java
public static final String TAG = "Kotlin";

//等同于
const val TAG = "Kotlin"
```

### by 关键字：类委托

**继承的实现导致的脆弱性**：当你**扩展一个类并重写某些方法**时，你的代码就**变得依赖你自己继承的那个类的实现细节**了。当系统不断演进并且基类的实现被修改或者新方法被添加进去时，你做出的关于类行为的假设会失效，所以你的代码也许最后就以不正确的行为而告终。

Kotlin 的设计就识别了这样的问题，并**默认将类视作final** 的。这**确保了只有那些设计成可扩展的类可以被继承**。当使用这样的类时，你会看见它是开放的，就会注意这些修改需要与派生类兼容。

但是你常常需要向其他类添加一些行为，即使它并没有被设计为可扩展的。

一个常用的实现方式以装饰器模式闻名：

- 这种模式的本质就是创建一个新类
- 实现与原始类一样的接口并将原来的类的实例作为一个字段保存。
- 与原始类拥有同样行为的方法不用被修改，只需要直接转发到原始类的实例。

这种方式的一个缺点是需要相当多的样板代码

是Kotlin 将委托作为一个语言级别的功能做了头等支持。无论什么时候实现一个接口，你都可以使用by 关键字将接口的实现委托到另一个对象。

```kotlin
class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList { // 将接口 Collection<T> 的实现方法委托给 innerList
    //这里可以只 override 需要的函数，不用像Java需实现所有方法
}
```

### Lambda 作为函数参数的代码块

```java
//java 使用匿名内部类实现监听器
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick (View view) {
        //do something
    }
};
```

```kotlin
/* View.OnClickListener 是只有一个抽象方法的接口
 * 这种接口被称为函数式接口，或 SAM 接口，SAM 代表 单抽象方法
 * 
 * SAM 构造方法是编译器生成的 SAM接口构造函数
 * 接收一个 lambda 作为参数，并返回实现 SAM接口实例
 * 
 * 注意：
 *   如果 lambda 没有访问任何来自定义它的函数的变量，相应的匿名类实例可以在多次调用间重用
 *   如果 lambda 从包围它的作用域捕捉了变量，每次调用就都会创建新对象
 */
button.setOnClickListener {
    //do something
}

//上面实现等同于下面实现
button.setOnClickListener(object : View.OnClickListener() {
    ovveride fun onClick(v: View?){
        //do something
    }
})

//Kotlin 1.4 以后才可以使用 fun 声明 SAM 接口，这才支持接口的 SAM 转化
fun interface A { ... }
```

kotlin lambda函数简化写法：

- 当 lambda 函数为末尾参数时，可以提取到括号外
- 当 lambda 函数为唯一实参时，可以省略括号
- 当参数类型可以由上下文推导出来时，可以省略参数类型
- 当上下文期望的是只有一个参数的 lambda ，且这个参数的类型可以推到出来，可以省略参数，会生成 it 代替该参数

### with 与 apply

```kotlin
//这个例子中，你调用了result 实例上好几个不同的方法，而且每次调用都要重复result 这个名称
fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'··'z'){
        result.append(letter)
        result.append("\nNow I know the alphabet !")
    }
    return result toString ()
}

/* with 结构看起来像是一种特殊的语法结构，但它实际上是一个接收两个参数的函数：
 * 这个例子中两个参数分别是stringBuilder 和一个lambda
 * 
 * with 函数把它的第一个参数转换成作为第二个参数传给它的lambda 的接收者。
 */
fun alphabet(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) {
        for (letter in ' A ' ..  'z'){
            this.append(letter)//通过显式的“this”来调用接收者值的方法
        }
        append("\nNow I know the alphabet !")//也可以省略
        this.toString()
    }
}

/* apply 始终会返回作为实参传递给它的对象（换句话说，接收者对象）
 * apply 被声明成一个扩展函数。它的接收者变成了作为实参的l ambda 的接收者。
 * 
 * 在创建一个对象实例并需要用正确的方式初始化它的一些属性的时候。
 * 在Java 中，这通常是通过另外一个单独的 Builder 对象来完成的
 * 而在Kotlin 中，可以在任意对象上使用apply,完全不需要任何来自定义该对象的库的特别支持。
 */
fun alphabet() = StringBuilder().apply {
    for (letter in 'A' ..'Z') {
        append(letter)
        append("\nNow I know the alphabet !")
    }.toString()
```

