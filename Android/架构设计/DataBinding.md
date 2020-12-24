> 早在2015谷歌 I/O大会上，就介绍了一个新的框架DataBinding，从名字就可以看出来，这是一个数据绑定框架。我们为什么要使用DataBinding？1.再也不需要编写findViewById了，有人会说，已经有butterknife了，很好用。2.更新UI数据需切换至UI线程，也有人说，有rxjava了。但是DataBinding，不仅仅能解决这2个问题，它的核心优势在于，它解决了将数据分解映射到各个view的问题。什么个意思？**具体来说，就是针对每个Activity或者Fragment的布局，在编译阶段，会生成一个ViewDataBinding类的对象，该对象持有Activity要展示的数据和布局中的各个view的引用。同时还有如下优势：将数据分解到各个view、在UI线程上更新数据、监控数据的变化，实时更新，这样一来，你要展示的数据已经和展示它的布局紧紧绑定在了一起。我认为这才是DataBinding真正的魅力所在**。

下面通过一个小例子来让大家感受一下DataBinding

**1、在在Module的build.gradle android模块中添加如下配置**

```bash
android {
 dataBinding {
    enabled = true
 }
}
```

**2、创建一个简单的JavaBean对象，姑且就叫UserBean吧**

```cpp
public class UserBean {
    private String name; //姓名
    private int age; //年龄

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

**3、使用了DataBinding之后的Activity的布局文件activity_main.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="user"
            type="com.zx.databindingdemo.bean.UserBean" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user.name}" />

        <!--注意：这里age是int类型，必须转化为String，否则会运行时异常-->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{String.valueOf(user.age)}" />
    </LinearLayout>
</layout>
```

这里和以前使用的xml不同，根节点变成了layout，里面包括了data节点和传统的布局。这里的data节点作用是连接 View 和 Modle 的桥梁。在这个data节点中声明一个variable变量，那值就可以轻松传到布局文件中来了。而且TextView中没有给控件定义id，而是在text的时候用了@{ }的方法，在括号里面直接引用UserBean对象的属性即可完成赋值。

**4、MainActivity**

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        UserBean userBean = new UserBean ("张三", "25");
        binding.setUser(userBean );
    }
}
```

这个activity很简洁，没有了没有了控件的初始化的findViewById或者butterknife的那一堆注解，也没有了TextView的setText(),也就2行代码而已。大家应该已经看见了，这里用DataBindingUtil.setContentView代替了setContentView，然后创建一个 UserBean 对象，通过 binding.setUser(userBean) 与 variable 进行绑定。注意：这个ActivityMainBinding 是如何生成的呢？他是继承ViewDataBinding，这个类的生成是有规则的，它是根据对应的布局文件的名字生成的，比如：activity_main-->ActivityMainBinding 、fragment-->FragmentBinding即：第一个单词首字母大写，第二个单词首字母大写，最后都会拼上Binding就是生成的Binding类。

**运行结果**

![img](https:////upload-images.jianshu.io/upload_images/4134622-192844ff0e9f530d.png?imageMogr2/auto-orient/strip|imageView2/2/w/240/format/webp)

看到这里，估计也应该有人被他的简洁吸引了吧。下面对可能产生的疑惑进行解释

**1. 为什么配置了 dataBinding{enabled = true}之后就可以使用dataBinding方式进行开发了？**

> Android Studio中是依靠gradle来管理项目的，在创建一个项目时，从开始创建一直到创建完毕，整个过程是需要执行很多个gradle task的，这些task有很多是系统预先帮我们定义好的，比如build task，clean task等，DataBinding相关的task也是系统预先帮我们定义好的，但是默认情况下，DataBinding相关的task在task列表中是没有的，因为我们没有开启dataBinding,但是一旦我们通过 dataBinding{enabled = true}的方式开启DataBinding之后，DataBinding相关的task就会出现在task列表中，每当我们执行编译之类的操作时，就会执行这些dataBinding Task, 这些task的作用就是检查并生成相关dataBinding代码，比如dataBindingExportBuildInfoDebug这个task就是用来导出debug模式下的build信息的。

**2. ActivityMainBinding这个类从哪来的？**

> 通过第一个问题的解释，我们也就知道了ActivityMainBinding这个类其实是系统帮我们自动生成的。
>  但是如果你在实际编写代码的过程中，你会发现并没有执行编译、运行之类等操作，ActivityMainBinding这个类就直接能用了，竟然还有这种操作？其实是Android Studio 这个IDE自动帮我们做了这一步，在默认情况下，系统会使用Android Studio为我们自动生成databinding相关的代码，但是这种方式生成的代码不能调试，如果你想通过点击ActivityMainBinding跳转到它的源码中，你会发现并不能如你所愿，而是会跳转到对应的布局文件中。那么如果我们确实要查看ActivityMainBinding的源码并且还想调试，我们就需要通过另外一种方式：手动编译代码。这两种方式可以通过Android Studio的设置面板修改。

**3.DataBinding与ButterKnife的区别**

> ButterKnife很多人都用过，在以前的findViewById的时代，我们厌烦了写这些重复的代码，当有了ButterKnife之后，我们采用注解的方式来查找控件和注册监听，配合ButterKnife的插件，大大提升了我们的开发效率，一度成为开发神器，但是有了DataBinding之后，你会发现使用ButterKnife开发还是不够简洁：比如你需要给很多TextView setText()要获取editText的内容前必须要获取editText对象，给view设置监听前也必须要获取这个view对象等等。但是使用了DataBinding之后，这些冗余的代码统统都可以得到简化，从而真正的只需要专注于你的业务逻辑的处理即可。

