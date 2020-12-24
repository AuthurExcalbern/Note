## Kotlin 对比 Java 的 类继承结构

### Katlin 中的接口

- 使用interface 关键字而不是class 来声明一个Kotlin 的接口
- 在Kotlin 中使用override 修饰符是强制要求
- 接口的方法可以有一个默认实现

```kotlin
interface Clickable {
    fun click()//普通方法
    fun showoff() = println("I am clickable!")//带默认实现
}

class Button : Clickable, Focusable {
    override fun click() = println ("I was clicked")
    override fun showoff() {//同样继承有不止一个实现，要提供一个显示实现 -> 防止冲突
        super<Clickable>.showoff()//使用 <> 表明是调用哪一个父类的 showoff()
        super<Focusable>.showoff()
    }
}
```

### open 、final 和abs t ract 修饰符：默认为final

对基类进行修改会导致子类不正确的行为，这就是所谓的脆弱的基类问题

Java 的类和方法默认是open 的，而Kotlin 中默认都是final 的。

### 可见性修饰符：默认为public

Java 中的默认可见性一一包私有，在Kotlin 中井没有使用。Kotlin 只把包作为在命名空间里组织代码的一种方式使用，并没有将其用作可见性控制。

作为替代方案， Kotlin 提供了一个新的修饰符， `internal` ，表示**只在模块内部可见**

internal 可见性的优势在于它提供了对模块实现细节的真正封装。使用Java时，这种封装很容易被破坏，因为外部代码可以将类定义到与你代码相同的包中，从而得到访问你的包私有声明的权限。

| 修饰符          | 类成员       | 顶层声明       |
| --------------- | ------------ | -------------- |
| public （默认） | 所有地方可见 | 类成员顶层声明 |
| internal        | 模块中可见   | 所有地方可见   |
| protected       | 子类中可见   | -              |
| private         | 类中可见     | 文件中可见     |

protected 修饰符在Java 和Kotlin 中不同的行为。在Java 中，**可以从同一个包中访问一个protected 的成员**，但是Kotlin 不允许这样做。在Kotlin 中可见性规则非常简单， protected 成员**只在类和它的子类中可见**。同样还要注意的是类的扩展函数不能访问它的private 和protected 成员。

### 内部类和嵌套类：默认是嵌套类

与Java对比，**Kotlin 的嵌套类不能访问外部类的实例**

```kotlin
interface State: Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}
```

```java
/* 如果你试图序列化声明的按钮的状态
 * 会得到一个java.io.NotSerializableException : Button 异常
 * 
 * ButtonState 类隐式地存储了它的外部Button 类的引用
 * 
 * ButtonState 不能被序列化:
 *   因为Button不是可序列化的，并且它的引用破坏了ButtonState 的序列化
 * 
 * 要修复这个问题，你需要声明ButtonState 类是static 的。
 * 将一个嵌套类声明为static 会从这个类中删除包围它的类的隐式引用。
 */
public class Button implements View {
    @Override
    public State getCurrentState() {
        return new ButtonState();
    }
    
    @Override
    public void restoreState(State state) { /* ... */ }
    
    // 你定义了实现State 接口的ButtonState 类，井且持有Button 的特定信息
    public class ButtonState implements State { /* ... */ }
}
```

```kotlin
/* Kotlin 中没有显式修饰符的嵌套类与Java 中的static 嵌套类是一样的。
 * 要把它变成一个内部类来持有一个外部类的引用的话需要使用inner 修饰符
 * 
 * Java -> Kotlin
 * static class A -> class A
 * class A -> inner class A
 * 
 * 在Kotlin 中引用外部类实例的语法也与Java 不同。
 * 需要使用this@Outer 从Inner 类去访问Outer 类
 */
class Button : View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { /* ... */ }
    class ButtonState : State  { /* ... */ } //等同Java静态嵌套类
}
```

### 密封类

```kotlin
sealed class Expr {
    //将所有可能出现的子类作为嵌套类列出
    class Num(val value ：Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}
```

密封类不能在类外部拥有子类，在这种情况下， Expr 类有一个只能在类内部调用的private 构造方法