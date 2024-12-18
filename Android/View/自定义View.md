## 1. 自定义View的分类

自定义View一共分为两大类，具体如下图：

<img src="../img/自定义View.webp" style="zoom:67%;" />



------

## 2. 具体介绍 & 使用场景

对于自定义View的类型介绍及使用场景如下图：

![](../img/自定义View1.webp)

------

## 3. 使用注意点

在使用自定义View时有很多注意点（坑），希望大家要非常留意：

<img src="../img/自定义View2.webp" style="zoom: 80%;" />

### 3.1 支持特殊属性

- 支持wrap_content
   如果不在`onMeasure（）`中对`wrap_content`作特殊处理，那么`wrap_content`属性将失效

> 具体原因请看文章：[为什么你的自定义View wrap_content不起作用？](https://www.jianshu.com/p/ca118d704b5e)

- 支持padding & margin
   如果不支持，那么`padding`和`margin`（ViewGroup情况）的属性将失效

> 1. 对于继承View的控件，padding是在draw()中处理
> 2. 对于继承ViewGroup的控件，padding和margin会直接影响measure和layout过程

### 3.2 多线程应直接使用post方式

View的内部本身提供了post系列的方法，完全可以替代Handler的作用，使用起来更加方便、直接。

### 3.3 避免内存泄露

主要针对View中含有线程或动画的情况：**当View退出或不可见时，记得及时停止该View包含的线程和动画，否则会造成内存泄露问题**。

> 启动或停止线程/ 动画的方式：
>
> 1. 启动线程/ 动画：使用`view.onAttachedToWindow（）`，因为该方法调用的时机是当包含View的Activity启动的时刻
> 2. 停止线程/ 动画：使用`view.onDetachedFromWindow（）`，因为该方法调用的时机是当包含View的Activity退出或当前View被remove的时刻

### 3.4 处理好滑动冲突

当View带有滑动嵌套情况时，必须要处理好滑动冲突，否则会严重影响View的显示效果。

------

## 4. 具体实例

接下来，我将用自定义View中最常用的**继承View**来说明自定义View的具体应用和需要注意的点

### 4.1 继承VIew的介绍

![](../img/自定义View3.webp)

在下面的例子中，我将讲解：

- 如何实现一个基本的自定义View（继承VIew）
- 如何自身支持wrap_content & padding属性
- 如何为自定义View提供自定义属性（如颜色等等）
- 实例说明：画一个实心圆

### 4.2 具体步骤

1. 创建自定义View类（继承View类）
2. 布局文件添加自定义View组件
3. 注意点设置（支持wrap_content & padding属性自定义属性等等）

下面我将逐个步骤进行说明：
 **步骤1：创建自定义View类（继承View类）**

*CircleView.java*

```java
// 用于绘制自定义View的具体内容
// 具体绘制是在复写的onDraw（）内实现

public class CircleView extends View {

    // 设置画笔变量
    Paint mPaint1;

    // 自定义View有四个构造函数
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CircleView(Context context){
        super(context);

        // 在构造函数里初始化画笔的操作
        init();
    }


// 如果View是在.xml里声明的，则调用第二个构造函数
// 自定义属性是从AttributeSet参数传进来的
    public CircleView(Context context,AttributeSet attrs){
        super(context, attrs);
        init();

    }

// 不会自动调用
// 一般是在第二个构造函数里主动调用
// 如View有style属性时
    public CircleView(Context context,AttributeSet attrs,int defStyleAttr ){
        super(context, attrs,defStyleAttr);
        init();
    }


    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public  CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // 画笔初始化
    private void init() {

        // 创建画笔
        mPaint1 = new Paint ();
        // 设置画笔颜色为蓝色
        mPaint1.setColor(Color.BLUE);
        // 设置画笔宽度为10px
        mPaint1.setStrokeWidth(5f);
        //设置画笔模式为填充
        mPaint1.setStyle(Paint.Style.FILL);

    }


    // 复写onDraw()进行绘制  
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

       // 获取控件的高度和宽度
        int width = getWidth();
        int height = getHeight();

        // 设置圆的半径 = 宽,高最小值的2分之1
        int r = Math.min(width, height)/2;

        // 画出圆（蓝色）
        // 圆心 = 控件的中央,半径 = 宽,高最小值的2分之1
        canvas.drawCircle(width/2,height/2,r,mPaint1);

    }

}
```

特别注意：

1. View的构造函数一共有4个，具体使用请看：[深入理解View的构造函数](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.jcodecraeer.com%2Fa%2Fanzhuokaifa%2Fandroidkaifa%2F2016%2F0806%2F4575.html)和
    [理解View的构造函数](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.cnblogs.com%2Fangeldevil%2Fp%2F3479431.html%23three)
2. 对于绘制内容为何在复写onDraw()里实现，具体请看我写的文章：[自定义View Draw过程- 最易懂的自定义View原理系列（4）](https://www.jianshu.com/p/95afeb7c8335)

**步骤2：在布局文件中添加自定义View类的组件**

*activity_main.xml*

```jsx
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="scut.carson_ho.diy_view.MainActivity">

<!-- 注意添加自定义View组件的标签名：包名 + 自定义View类名-->
    <!--  控件背景设置为黑色-->
    <scut.carson_ho.diy_view.CircleView
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:background="#000000"

</RelativeLayout>
```

**步骤3：在MainActivity类设置显示**

*MainActivity.java*

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

![](../img/自定义View4.webp)

效果图

好了，至此，一个基本的自定义View已经实现了。接下来继续看自定义View所有应该注意的点：

- 如何手动支持wrap_content属性
- 如何手动支持padding属性
- 如何为自定义View提供自定义属性（如颜色等等）

#### a. 手动支持wrap_content属性

先来看wrap_content & match_parent属性的区别

```cpp
// 视图的宽和高被设定成刚好适应视图内容的最小尺寸
android:layout_width="wrap_content"

// 视图的宽和高延伸至充满整个父布局
android:layout_width="match_parent"
// 在Android API 8之前叫作"fill_parent"
```

如果不手动设置支持`wrap_content`属性，那么`wrap_content`属性是不会生效（显示效果同`match_parent`）

> 具体原因 & 解决方案请看我写的文章：[为什么你的自定义View wrap_content不起作用？](https://www.jianshu.com/p/ca118d704b5e)

#### b. 支持padding属性

`padding`属性：用于设置控件内容相对控件边缘的边距；

> 区别与margin属性（同样称为：边距）：控件边缘相对父控件的边距（父控件控制），具体区别如下：

<img src="../img/自定义View5.webp" style="zoom:67%;" />

如果不手动设置支持padding属性，那么padding属性在自定义View中是不会生效的。

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="scut.carson_ho.diy_view.MainActivity">

    <scut.carson_ho.diy_view.CircleView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        /**  添加Padding属性，但不会生效 **/
        android:padding="20dp"
         />
</RelativeLayout>
```

**解决方案**

绘制时考虑传入的padding属性值（四个方向）。

> 在自定义View类的复写onDraw（）进行设置

*CircleView.java*

```java
// 仅看复写的onDraw（）
@Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // 获取传入的padding值
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();


        // 获取绘制内容的高度和宽度（考虑了四个方向的padding值）
        int width = getWidth() - paddingLeft - paddingRight ;
        int height = getHeight() - paddingTop - paddingBottom ;

        // 设置圆的半径 = 宽,高最小值的2分之1
        int r = Math.min(width, height)/2;

        // 画出圆(蓝色)
        // 圆心 = 控件的中央,半径 = 宽,高最小值的2分之1
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,r,mPaint1);

    }
```

<img src="../img/自定义View6.webp" style="zoom:50%;" />

#### c. 提供自定义属性

系统自带属性，如

```cpp
// 基本是以android开头
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#000000"
        android:padding="30dp"
```

- 但有些时候需要一些系统所没有的属性，称为自定义属性
- 使用步骤有如下：
  1. 在values目录下创建自定义属性的xml文件
  2. 在自定义View的构造方法中解析自定义属性的值
  3. 在布局文件中使用自定义属性

下面我将对每个步骤进行具体介绍

##### 步骤1：在values目录下创建自定义属性的xml文件

*attrs_circle_view.xml*

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--自定义属性集合:CircleView-->
    <!--在该集合下,设置不同的自定义属性-->
    <declare-styleable name="CircleView">
        <!--在attr标签下设置需要的自定义属性-->
        <!--此处定义了一个设置图形的颜色:circle_color属性,格式是color,代表颜色-->
        <!--格式有很多种,如资源id(reference)等等-->
        <attr name="circle_color" format="color"/>

    </declare-styleable>
</resources>
```

对于自定义属性类型 & 格式如下：

```xml
<-- 1. reference：使用某一资源ID -->
<declare-styleable name="名称">
    <attr name="background" format="reference" />
</declare-styleable>
// 使用格式
  // 1. Java代码
  private int ResID;
  private Drawable ResDraw;
  ResID = typedArray.getResourceId(R.styleable.SuperEditText_background, R.drawable.background); // 获得资源ID
  ResDraw = getResources().getDrawable(ResID); // 获得Drawble对象

  // 2. xml代码
<ImageView
    android:layout_width="42dip"
    android:layout_height="42dip"
    app:background="@drawable/图片ID" />

<--  2. color：颜色值 -->
<declare-styleable name="名称">
    <attr name="textColor" format="color" />
</declare-styleable>
// 格式使用
<TextView
    android:layout_width="42dip"
    android:layout_height="42dip"
    android:textColor="#00FF00" />

<-- 3. boolean：布尔值 -->
<declare-styleable name="名称">
    <attr name="focusable" format="boolean" />
</declare-styleable>
// 格式使用
<Button
    android:layout_width="42dip"
    android:layout_height="42dip"
    android:focusable="true" />

<-- 4. dimension：尺寸值 -->
<declare-styleable name="名称">
    <attr name="layout_width" format="dimension" />
</declare-styleable>
// 格式使用：
<Button
    android:layout_width="42dip"
    android:layout_height="42dip" />

<-- 5. float：浮点值 -->
<declare-styleable name="AlphaAnimation">
    <attr name="fromAlpha" format="float" />
    <attr name="toAlpha" format="float" />
</declare-styleable>
// 格式使用
<alpha
    android:fromAlpha="1.0"
    android:toAlpha="0.7" />

<-- 6. integer：整型值 -->
<declare-styleable name="AnimatedRotateDrawable">
    <attr name="frameDuration" format="integer" />
    <attr name="framesCount" format="integer" />
</declare-styleable>
// 格式使用
<animated-rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:frameDuration="100"
    android:framesCount="12"
 />

<-- 7. string：字符串 -->
<declare-styleable name="MapView">
    <attr name="apiKey" format="string" />
</declare-styleable>
// 格式使用
<com.google.android.maps.MapView
 android:apiKey="0jOkQ80oD1JL9C6HAja99uGXCRiS2CGjKO_bc_g" />

<-- 8. fraction：百分数 -->
<declare-styleable name="RotateDrawable">
    <attr name="pivotX" format="fraction" />
    <attr name="pivotY" format="fraction" />
</declare-styleable>
// 格式使用
<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:pivotX="200%"
    android:pivotY="300%"
 />


<-- 9. enum：枚举值 -->
<declare-styleable name="名称">
    <attr name="orientation">
        <enum name="horizontal" value="0" />
        <enum name="vertical" value="1" />
    </attr>
</declare-styleable>
// 格式使用
<LinearLayout
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
/>

<-- 10. flag：位或运算 -->
<declare-styleable name="名称">
    <attr name="windowSoftInputMode">
        <flag name="stateUnspecified" value="0" />
        <flag name="stateUnchanged" value="1" />
        <flag name="stateHidden" value="2" />
        <flag name="stateAlwaysHidden" value="3" />
        <flag name="stateVisible" value="4" />
        <flag name="stateAlwaysVisible" value="5" />
        <flag name="adjustUnspecified" value="0x00" />
        <flag name="adjustResize" value="0x10" />
        <flag name="adjustPan" value="0x20" />
        <flag name="adjustNothing" value="0x30" />
    </attr>
</declare-styleable>、
// 使用
<activity
    android:name=".StyleAndThemeActivity"
    android:label="@string/app_name"
    android:windowSoftInputMode="stateUnspecified | stateUnchanged　|　stateHidden" >

    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>



<-- 特别注意：属性定义时可以指定多种类型值 -->
<declare-styleable name="名称">
    <attr name="background" format="reference|color" />
</declare-styleable>
// 使用
<ImageView
    android:layout_width="42dip"
    android:layout_height="42dip"
    android:background="@drawable/图片ID|#00FF00" />
```

##### 步骤2：在自定义View的构造方法中解析自定义属性的值

> 此处是需要解析circle_color属性的值

```java
// 该构造函数需要重写
  public CircleView(Context context, AttributeSet attrs) {

        this(context, attrs,0);
        // 原来是：super(context,attrs);
        init();


public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载自定义属性集合CircleView
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);

        // 解析集合中的属性circle_color属性
        // 该属性的id为:R.styleable.CircleView_circle_color
        // 将解析的属性传入到画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）
        // 第二个参数是默认设置颜色（即无指定circle_color情况下使用）
        mColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);

        // 解析后释放资源
        a.recycle();

        init();
```

##### 步骤3：在布局文件中使用自定义属性

*activity_main.xml*

```jsx
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
  <!--必须添加schemas声明才能使用自定义属性-->
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="scut.carson_ho.diy_view.MainActivity"
    >
  
<!-- 注意添加自定义View组件的标签名：包名 + 自定义View类名-->
    <!--  控件背景设置为黑色-->
    <scut.carson_ho.diy_view.CircleView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        android:background="#000000"
        android:padding="30dp"

    <!--设置自定义颜色-->
        app:circle_color="#FF4081"
         />
</RelativeLayout>
```

<img src="../img/自定义View7.webp" style="zoom:50%;" />

至此，一个较为规范的自定义View已经完成了。

#### 完整代码下载

Carson_Ho的github：[自定义View的具体应用](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FCarson-Ho%2FDIY_View)

