# Kotlin与Java对比

在前面的内容里，我们已经看到了Java与Kotlin的互操作的基本方式。为了更好的认识Java与Kotlin这两门语言，我们在这里给出一些基本功能，同时使用Java与Kotlin来实现的代码实例。通过横向对比，从中我们可以看出它们的异同。

（此处可整理成表格形式）

## 打印日志

- Java

```java
System.out.print("Java");
System.out.println("Java");
```

- Kotlin

```kotlin
print("Kotlin")
println("Kotlin")
```

其实，Kotlin中的println函数是一个内联函数，它其实就是通过封装`java.lang.System`类的System.out.println来实现的。

```kotlin
@kotlin.internal.InlineOnly
public inline fun print(message: Any?) {
    System.out.print(message)
}
```

## 常量与变量

- Java

```java
String name = "KotlinVSJava";
final String name = "KotlinVSJava";
```

- Kotlin

```kotlin
var name = "KotlinVSJava"
val name = "KotlinVSJava"
```

## null声明

- Java

```java
String otherName;
otherName = null;
```

- Kotlin

```kotlin
var otherName : String?
otherName = null
```

## 空判断

- Java

```java
if (text != null) {
    int length = text.length();
}
```

- Kotlin

```kotlin
text?.let {
    val length = text.length
}
// 或者
val length = text?.length
```

在Kotlin中，我们只使用一个问号安全调用符号就省去了Java中烦人的`if - null` 判断。

## 字符串拼接

- Java

```java
String firstName = "Jack";
String lastName = "Chen";
String message = "My name is: " + firstName + " " + lastName;
```

- Kotlin

```kotlin
val firstName = "Jack"
val lastName = "Chen"
val message = "My name is: $firstName $lastName"
```

Kotlin中使用`$`和`${}`（花括号里面是表达式的时候）占位符来实现字符串的拼接，这个比在Java中每次使用加号来拼接要方便许多。

## 换行

- Java

```java
String text = "First Line\n" +
              "Second Line\n" +
              "Third Line";
```

- Kotlin

```kotlin
val text = """
        |First Line
        |Second Line
        |Third Line
        """.trimMargin()
```

## 三元表达式

- Java

```java
String text = x > 5 ? "x > 5" : "x <= 5";
```

- Kotlin

```kotlin
val text = if (x > 5)
              "x > 5"
           else "x <= 5"
```

## 操作符

- java

```java
final int andResult  = a & b;
final int orResult   = a | b;
final int xorResult  = a ^ b;
final int rightShift = a >> 2;
final int leftShift  = a << 2;
```

- Kotlin

```kotlin
val andResult  = a and b
val orResult   = a or b
val xorResult  = a xor b
val rightShift = a shr 2
val leftShift  = a shl 2
```

## 类型判断和转换（显式）

- Java

```java
if (object instanceof Car) {
}
Car car = (Car) object;
```

- Kotlin

```kotlin
if (object is Car) {
}
var car = object as Car
```

## 类型判断和转换 (隐式)

- Java

```java
if (object instanceof Car) {
   Car car = (Car) object;
}
```

- Kotlin

```kotlin
if (object is Car) {
   var car = object // Kotlin智能转换
}
```

Kotlin的类型系统具备一定的类型推断能力，这样也省去了不少在Java中类型转换的样板式代码。

## Range区间

- Java

```java
if (score >= 0 && score <= 300) { }
```

- Kotlin

```kotlin
if (score in 0..300) { }
```

## 更灵活的case语句

- Java

```java
    public String getGrade(int score) {
        String grade;
        switch (score) {
            case 10:
            case 9:
                grade = "A";
                break;
            case 8:
            case 7:
            case 6:
                grade = "B";
                break;
            case 5:
            case 4:
                grade = "C";
                break;
            case 3:
            case 2:
            case 1:
                grade = "D";
                break;
            default:
                grade = "E";
        }
        return grade;
    }
```

- Kotlin

```kotlin
fun getGrade(score: Int): String {
    var grade = when (score) {
        9, 10 -> "A"
        in 6..8 -> "B"
        4, 5 -> "C"
        in 1..3 -> "D"
        else -> "E"
    }
    return grade
}
```

## for循环

- Java

```java
for (int i = 1; i <= 10 ; i++) { }

for (int i = 1; i < 10 ; i++) { }

for (int i = 10; i >= 0 ; i--) { }

for (int i = 1; i <= 10 ; i+=2) { }

for (int i = 10; i >= 0 ; i-=2) { }

for (String item : collection) { }

for (Map.Entry<String, String> entry: map.entrySet()) { }
```

- Kotlin

```kotlin
for (i in 1..10) { }

for (i in 1 until 10) { }

for (i in 10 downTo 0) { }

for (i in 1..10 step 2) { }

for (i in 10 downTo 1 step 2) { }

for (item in collection) { }

for ((key, value) in map) { }
```

## 更方便的集合操作

- Java

```java
final List<Integer> listOfNumber = Arrays.asList(1, 2, 3, 4);
final Map<Integer, String> map = new HashMap<Integer, String>();
map.put(1, "Jack");
map.put(2, "Ali");
map.put(3, "Mindorks");
```

- Kotlin

```kotlin
val listOfNumber = listOf(1, 2, 3, 4)
val map = mapOf(1 to "Jack", 2 to "Ali", 3 to "Mindorks")
```

## 遍历

- Java

```java
// Java 7 
for (Car car : cars) {
  System.out.println(car.speed);
}

// Java 8+
cars.forEach(car -> System.out.println(car.speed));

// Java 7 
for (Car car : cars) {
  if (car.speed > 100) {
    System.out.println(car.speed);
  }
}

// Java 8+
cars.stream().filter(car -> car.speed > 100).forEach(car -> System.out.println(car.speed));
```

- Kotlin

```kotlin
cars.forEach {
    println(it.speed)
}

cars.filter { it.speed > 100 }
      .forEach { println(it.speed)}
```

## 方法(函数)定义

- Java

```java
void doSomething() {
   // 实现
}

void doSomething(int... numbers) {
   // 实现
}
```

- Kotlin

```kotlin
fun doSomething() {
   // 实现
}

fun doSomething(vararg numbers: Int) {
   // 实现
}
```

## 带返回值的方法（函数）

- Java

```java
int getScore() {
   // logic here
   return score;
}
```

- Kotlin

```kotlin
fun getScore(): Int {
   // logic here
   return score
}

// 单表达式函数
fun getScore(): Int = score
```

另外，Kotlin中的函数是可以直接传入函数参数，同时可以返回一个函数类型的。

## constructor 构造器

- Java

```java
public class Utils {

    private Utils() { 
      // 外部无法来调用实例化
    }
    
    public static int getScore(int value) {
        return 2 * value;
    }
    
}
```

- Kotlin

```kotlin
class Utils private constructor() {
    companion object {
        fun getScore(value: Int): Int {
            return 2 * value
        }
        
    }
}

// 或者直接声明一个object对象
object Utils {
    fun getScore(value: Int): Int {
        return 2 * value
    }
}

/**
 * 在Student的主构造函数中增加name和age时,不能将他们再声明成val
 * 因为主构造函数中声明的val或者var将自动成为该类的字段,这就会导致和父类中同名的字段冲突
 * 因此name和age不需要加任何关键字,让他的作用域仅限定在主构造函数当中
 */
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) {
    init {
        println("sno is $sno")
        println("grade is $grade")
    }
}
```

## JavaBean与Kotlin数据类

这段Kotlin中的数据类的代码：

```kotlin
data class Developer(val name: String, val age: Int)
```

对应下面这段Java实体类的代码：

- Java

```java
public final class Developer {
   @NotNull
   private final String name;
   private final int age;

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final int getAge() {
      return this.age;
   }

   public Developer(@NotNull String name, int age) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      super();
      this.name = name;
      this.age = age;
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   public final int component2() {
      return this.age;
   }

   @NotNull
   public final Developer copy(@NotNull String name, int age) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      return new Developer(name, age);
   }

   // $FF: synthetic method
   // $FF: bridge method
   @NotNull
   public static Developer copy$default(Developer var0, String var1, int var2, int var3, Object var4) {
      if((var3 & 1) != 0) {
         var1 = var0.name;
      }

      if((var3 & 2) != 0) {
         var2 = var0.age;
      }

      return var0.copy(var1, var2);
   }

   public String toString() {
      return "Developer(name=" + this.name + ", age=" + this.age + ")";
   }

   public int hashCode() {
      return (this.name != null?this.name.hashCode():0) * 31 + this.age;
   }

   public boolean equals(Object var1) {
      if(this != var1) {
         if(var1 instanceof Developer) {
            Developer var2 = (Developer)var1;
            if(Intrinsics.areEqual(this.name, var2.name) && this.age == var2.age) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
```

