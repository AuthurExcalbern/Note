## Object 关键字

Object 关键字核心理念：**定义一个类并同时创建一个实例**（换句话说就是一个对象）。

### 对象声明

对象声明将类声明与该类的单一实例声明结合到了一起。

```kotlin
object Payroll {
    val a = ...//一个对象声明也可以包含属性、方法、初始化语句块等的声明
    fun b() {...}
    
    //唯一不允许的就是构造方法（包括主构造方法和从构造方法）
}
```

同样可以在类中声明对象。这样的对象同样只有一个单一实例；它们在每个容器类的实例中并不具有不同的实例。

### 伴生对象

Kotlin 中的类不能拥有静态成员： Java 的static 关键字并不是Kotlin 语言的一部分。作为替代，

-  Kotlin 依赖包级别函数（在大多数情形下能够替代Java 的静态方法）
- 对象声明（在其他情况下替代Java 的静态方法，同时还包括静态宇段）

在大多数情况下，还是推荐使用顶层函数。但是顶层函数不能访问类的private成员

因此如果你需要写一个可以**在没有类实例的情况下调用但是需要访问类内部的函数**，可以将其写成那个类中的对象声明的成员。

在类中定义的对象之一可以使用一个特殊的关键字来标记： `companion` 。

如果这样做，就获得了直接通过容器类名称来访问这个对象的方法和属性的能力，不再需要显式地指明对象的名称。最终的语法看起来非常像Java 中的静态方法调用：

```kotlin
class A {
    companion object {//伴生对象
        fun bar() {//可以访问类中的所有private 成员，包括private 构造方法
            println("Companion ob ject called")
        }
    }
}
```

### 对象表达式

`object` 关键字不仅仅能用来声明单例式的对象，还能用来声明匿名对象。

匿名对象替代了Java 中匿名内部类的使用。

```kotlin
button.setOnClickListener {object : View.OnClickListener() {
    ovveride fun onClick(v: View?){
        //do something
    }
}
```

