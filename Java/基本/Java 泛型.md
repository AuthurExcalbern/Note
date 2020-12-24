# Java 泛型

## 实现原理

### 类型擦除

```java
//简单的验证下类型擦除
ArrayList<String> aString=new ArrayList<String>();     
ArrayList<Integer> aInteger=new ArrayList<Integer>();     
System.out.println(aString.getClass()==aInteger.getClass()); //发现结果为true。
```

在编译期间，所有的泛型信息都会被**擦除**，List<Integer>和List<String>类型，在编译后都会变成List类型（原始类型）。Java中的泛型基本上都是在编译器这个层次来实现的，这也是Java的泛型被称为**“伪泛型”**的原因。

#### 那 什么是“原始类型”呢？

原始类型就是泛型类型擦除了泛型信息后，在字节码中真正的类型。无论何时定义一个泛型类型，相应的原始类型都会被自动提供。原始类型的名字就是删去类型参数后的泛型类型的类名。擦除**类型变量**，并替换为***限定类型***（T为无限定的***类型变量***，用Object替换）。

```java
//泛型类型  ,泛型为超类Object
class Bean<T> {private T value;    }
//原始类型  
class Bean {private Object value;    }
```

```java
//泛型类型 , 泛型为数值类型,支持byte，double，float，int，long，short
class Bean<T extends Number> {private T value;    }
//原始类型  
class Bean {private Number value;    }
```

## 泛型使用注意事项

### 1.不可以在静态变量，静态方法中直接使用

```java
public class Test<T> {      
    public static T key;   //编译错误,静态存在的时候对象还没有实例化，不知道泛型是什么      
    public static  T show(T one){ //编译错误      
        return null;      
    } 
    public static <T>T show(T one){//这是正确的,此处使用的泛型T是方法自身的T      
        return null;      
    }      
}    
```

### 2.泛型不能是基本数据类型

比如：没有List<double>，只有List<Double>。因为当类型擦除后，List的原始类中的类型变量（T）替换为Object，但Object类型不能存储double值。

**基本数据类型**：int、short、float、double、long、boolean、byte、char

**对应包装类**：Integer、Short、Float、Double、Long、Boolean、Byte、Character

**String** 是引用数据类型

### 3.泛型类型引用传递

```java
ArrayList<String> aList1=new ArrayList<Object>();//编译错误,要求一致不支持转型，违背设计原则
ArrayList<Object> aList1=new ArrayList<String>();//编译错误   
```

其实这个问题基本不会存在，因为(在java6以上)后面可以直接使用new ArrayList<>()，IDE会直接默认写好

```java
List<String> rawList = new ArrayList();
Java 5的javac编译器会产生类型未检查的警告
  注意: test.java使用了未检查或称为不安全的操作;
这种警告可以使用@SuppressWarnings("unchecked")注解来屏蔽。
```

### 4.运行时类型查询

```java
ArrayList<String> arrayList=new ArrayList<>();
if( arrayList instanceof ArrayList<String>) //运行报错
  //因为类型擦除之后，ArrayList<String>只剩下原始类型，泛型信息String不存在了。
  //解决方案
if( arrayList instanceof ArrayList<?>)//正确，？为通配符，也即非限定符。
```

### 5.限定通配符和非限定通配符

```java
List<? extends T>可以接受任何继承自T的类型的List
List<? super T>可以接受任何T的父类构成的List
  //例子
  List<? extends Number>可以接受List<Integer>或List<Float>//Byte，Double，Long，Short 几种数值类型
```

## 泛型使用场景

### 1.集合框架/数组

```java
ArrayList<Object> aList1=new ArrayList<>(); 
```

```java
// 接收可变参数 返回泛型数组
public static <T> T[] fun1(T...arg){ return arg ;  }
//调用方法
Integer i[] = fun1(1,2,3,4,5,6)
```

### 2.泛型类 ——对象的引用传递

```java
//进行引用传递的时候泛型必须匹配才可以传递，否则无法传递。
class Info<T>{
 private T var ; // 定义泛型变量 
  public void setVar(T var){ 
  this.var = var ; 
  } 
}
```

### 3.泛型接口

泛型接口很类似泛型类：**访问权限 +interface +接口名称 + <泛型标示>{}**

```java
// 泛型接口  定义抽象方法，抽象方法的返回值就是泛型类型
interface Info<T> {  
    public T getVar();    
}
//实现类__方式1   getter/setter方法略
class InfoImpl implements Info<String> {
    private String mVar;
    public InfoImpl(String var) {this.mVar=var;}
}
//实现类__方式2  getter/setter方法略
class InfoImpl2<T> implements Info<T> { 
    private T mVar;
    public InfoImpl2(T var) {this.mVar=var; }
}
//测试类
public class Test {
    public static void main(String arsg[]) {
        Info i = new InfoImpl("hello world"); // 通过子类实例化对象
        System.out.println("方式一内容：" + i.getVar());
        Info<String> j = new InfoImpl<String>("soyoungboy");
       System.out.println("方式二内容：" + j.getVar());
    }
}
```

### 4.泛型方法(可用在2，3 中也可用在其他位置)

```java
//来个简单案例。泛型方法（对象设置特定属性）
    public static <T extends Number> T setValue(T x){    
        return x;    
    }   
```

```java
//T, E or K,V等被广泛认可的类型占位符
public V put(K key, V value) {  
    return cache.put(key, value);  
}  
```

### 5.泛型嵌套 —— 泛型类的深入使用

```java
//一个普通的泛型类，接收两个泛型变量，省略setter方法
class Info<T, V> {
    private T var;
    private V value;
    public Info(T var, V value) {
        this.var =var;
        this.value =value;
    }
    public T getVar() {return var;}
    public V getValue() { return value;}
}
//一个嵌套了泛型类的泛型类user,  可能存在一个mannager类也嵌套info泛型类
class User<S> {
    public S info;
    public User(S info) {this.info=info;}
    public S getInfo() {return info;}
}
//测试类
public class test {
    public static void main(String args[]) {
        User<Info<String, Integer>> d = null;
        Info<String, Integer> i = null;
        i = new Info<String, Integer>("feisher", 29);
        d = new User<Info<String, Integer>>(i);
        System.out.println("姓名：" + d.getInfo().getVar());
        System.out.println("年龄：" + d.getInfo().getValue());
    }
}
```