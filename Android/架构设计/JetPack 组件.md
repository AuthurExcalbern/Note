### 前言

**Android Jetpack**想必大家都耳熟能详了，`Android KTX`，`LiveData`，`Room`等等一系列库都是出自 `Jetpack`。那么`Jetpack`到底是什么？又包含哪些你还没用过的东西？`Google`推出这个的原因又是什么？今天我们就一起来完善一下我们脑中的`Jetpack构图`。（篇幅较长，建议点赞关注Mark哦🐶 ）

### 介绍

2018年谷歌I/O，`Jetpack`横空出世，官方介绍如下：

> Jetpack 是一套库、工具和指南，可帮助开发者更轻松地编写优质应用。这些组件可帮助您遵循最佳做法、让您摆脱编写样板代码的工作并简化复杂任务，以便您将精力集中放在所需的代码上。

好好琢磨这段介绍就能解释我们刚才的问题。

`Jetpack`到底是什么？

- 是一套库、工具和指南。说白了就是一系列的库或者工具集合，而且这些工具是作为我们优质应用的指南，相当于`官方推荐`做法。

`google`推出这个系列的原因是什么？

- 规范开发者更快更好的开发出优质应用。一直以来，`Android开发`都充斥了大量的不规范的操作和重复代码，比如生命周期的管理，开发过程的重复，项目架构的选择等等。所以`Google`为了规范开发行为，就推出这套指南，旨在让开发者们能够`更好，更快，更规范`地开发出优质应用。

当然，这两年的实践也确实证明了`Jetpack`做到了它介绍的那样，便捷，快速，优质。所以我们作为开发者还是应该早点应用到这些工具，提高自己的`开发效率`，也规范我们自己的开发行为。下面我们就一起了解下`Jetpack`的所有工具指南。GOGOGO！

先来一张官网的总揽图：
 (`温馨提示❤️`本文严格按照下图顺序对组件进行分析，有需要的可以从目录进入或者直接搜索查看)

![img](https:////upload-images.jianshu.io/upload_images/8690467-1add68bbed059d2d.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

Jetpack.jpg

### Jetpack-基础组件

#### Android KTX

> Android KTX 是包含在 Android Jetpack 及其他 Android 库中的一组 Kotlin 扩展程序。KTX 扩展程序可以为 Jetpack、Android 平台及其他 API 提供简洁的惯用 Kotlin 代码。为此，这些扩展程序利用了多种 Kotlin 语言功能

所以`Android KTX`就是基于`kotlin`特性而扩展的一些库，方便开发使用。

举🌰：
 现在有个需求，让两个`Set数组`的数据相加，赋值给新的`Set数组`。正常情况下实现功能：



```kotlin
    val arraySet1 = LinkedHashSet<Int>()
    arraySet1.add(1)
    arraySet1.add(2)
    arraySet1.add(3)

    val arraySet2 = LinkedHashSet<Int>()
    arraySet2.add(4)
    arraySet2.add(5)
    arraySet2.add(6)

    val combinedArraySet1 = LinkedHashSet<Int>()
    combinedArraySet1.addAll(arraySet1)
    combinedArraySet1.addAll(arraySet2)
```

这代码真是又臭又长🙄️，没关系，引入`Collection KTX`扩展库再实现试试：



```kotlin
    dependencies {
        implementation "androidx.collection:collection-ktx:1.1.0"
    }
    
    // Combine 2 ArraySets into 1.
    val combinedArraySet = arraySetOf(1, 2, 3) + arraySetOf(4, 5, 6)
```

就是这么简单，用到`kotlin`的扩展函数扩展属性，扩展了集合相关的功能，简化了代码。
 由于`kotlin`的各种特性，也就促成了一系列的扩展库，还包括有`Fragment KTX，Lifecycle KTX`等等。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fkotlin%2Fktx)    
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fktx)

#### AppCompat

不知道大家发现没，原来Activity继承的Activity类都被要求改成继承`AppCompatActivity`类。这个AppCompatActivity类就属于`AppCompat`库，主要包含对Material Design界面实现的支持，相类似的还包括`ActionBar，AppCompatDialog和ShareActionProvider`，一共四个关键类。

那么AppCompatActivity类到底对比Activity类又什么区别呢？

- `AppCompatActivity`，类似于原来的ActionBarActivity，一个带标题栏的Activity。具体就是带Toolbar的Activity。

这里还有个`ShareActionProvider`大家可能用得比较少，这个类是用于在菜单栏集成分享功能。
 通过`setShareIntent(Intent intent)`方法可以在Menu里设置你要分享的内容。具体用法可以参考[官网说明](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Freference%2Fandroidx%2Fappcompat%2Fwidget%2FShareActionProvider)。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Fsupport-library%2Fpackages%23v7-appcompat)

#### Auto

> 让您在编写应用时无需担心特定于车辆的硬件差异（如屏幕分辨率、软件界面、旋钮和触摸式控件）。用户可以通过手机上的 Android Auto 应用访问您的应用。或者，当连接到兼容车辆时，运行 Android 5.0（或更高版本）的手持设备上的应用可以与通过 Android Auto 投射到车辆的应用进行通信。

`Android Auto`，这个大家估计有点陌生。但是说到 CarPlay大家是不是很熟悉呢？没错，`Android Auto`是Google出的车机手机互联方案。国内销售的汽车大多数没有搭载谷歌的Android Auto墙太高，触及不到），所以我们接触的很少。但是国外还是应用比较广泛的。

所以这一模块就是用于开发`Android Auto`相关应用的，比如音乐播放APP，即时通信APP之类，可以与车载系统通信。

怎么让你的应用支持Android Auto？



```java
    //添加
    <meta-data android:name="com.google.android.gms.car.application"
            android:resource="@xml/automotive_app_desc"/>
            
    <automotiveApp>
        <uses name="media"/>
    </automotiveApp>        
```

然后就可以进行相关开发了。怎么测试呢？总不能让我去汽车里面测试吧。。
 放心，官方提供了模拟器—`Android Auto Desktop Head Unit emulator`（简称DHU），在`SDK Tools`里面可以下载。
 如果你感兴趣，可以去[官网文档了解更多](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fcars)。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fcars)

#### 检测

> 使用 Jetpack 基准库，您可以在 Android Studio 中快速对 Kotlin 或 Java 代码进行基准化分析。该库会处理预热，衡量代码性能，并将基准化分析结果输出到 Android Studio 控制台。

这个模块说的是一个测试性能的库—`Benchmark`，其实就是测试耗时时间，所以我们可以用来测试`UI性能`，图片加载性能等等。现在我们来实现一个测试图片加载性能的🌰：

为了方便我们直接创建一个Benchmark模块，右键`New > Module >Benchmark Module`。
 这样就会帮我们导入好库了，然后我们在`androidTest—java`目录下创建我们的测试用例类BitmapBenchmark，并添加两个测试用例方法。



```kotlin
    androidTestImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.benchmark:benchmark-junit4:1.0.0'
    
private const val JETPACK = "images/test001.jpg"

@LargeTest
@RunWith(AndroidJUnit4::class)
class BitmapBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var bitmap: Bitmap

    @Before
    fun setUp() {
        val inputStream = context.assets.open(JETPACK)
        bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
    }


    @Test
    fun bitmapGetPixelBenchmark() {
        val pixels = IntArray(100) { it }
        benchmarkRule.measureRepeated {
            pixels.map { bitmap.getPixel(it, 0) }
        }
    }

   //测试100像素图像绘制耗时
    @Test
    fun bitmapGetPixelsBenchmark() {
        val pixels = IntArray(100) { it }
        benchmarkRule.measureRepeated {
            bitmap.getPixels(pixels, 0, 100, 0, 0, 100, 1)
        }
    }
}    
```

然后右键`BitmapBenchmark`类运行，注意需要在真机运行，控制台打印出两个方法的耗时



```kotlin
Started running tests

benchmark:         2,086 ns BitmapBenchmark.bitmapGetPixelsBenchmark
benchmark:        70,902 ns BitmapBenchmark.bitmapGetPixelBenchmark
Tests ran to completion.
```

这就是`Benchmark`库的简单使用，我理解`benchmark`这个模块是在单元测试的基础上可以提供更多性能测试的功能，比如执行时间等。但是实际使用的话好像大家都用的比较少？以后会多尝试看看，如果有懂的老铁也可以评论区科普下😁。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fstudio%2Fprofile%2Fbenchmark)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fbenchmark)

#### 多dex处理

这个应该大家都很熟悉，`65536方法`数限制。由于 65536 等于64 X 1024，因此这一限制称为“64K 引用限制”。意思就是单个`DEX 文件`内引用的方法总数限制为65536，超过这个方法数就要打包成多个dex。

解决办法：

- `Android5.0`以下，需要添加MultiDex支持库。具体做法就是引入库，启用MultiDex，修改Application。
- `Android5.0`以上，默认启动MultiDex，不需要导入库。

问题来了？为什么5.0以上就默认支持这个功能了呢？

- `Android 5.0`之前的平台版本使用Dalvik运行时执行应用代码，Dalvik 将应用限制为每个 APK 只能使用一个 classes.dex 字节码文件，为了绕过这一限制，只有我们手动添加MultiDex支持库。
- `Android 5.0`及更高版本使用名为 ART 的运行时，它本身支持从APK 文件加载多个 DEX 文件。ART在应用安装时执行预编译，扫描classesN.dex文件，并将它们编译成单个.oat 文件，以供Android设备执行。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fstudio%2Fbuild%2Fmultidex)

#### 安全

> Security 库提供了与读取和写入静态数据以及密钥创建和验证相关的安全最佳做法实现方法。

这里的安全指的是数据安全，涉及到的库为`Security 库`，具体就是安全读写文件以及安全设置共享偏好SharedPreferences。
 不知道大家以前加密文件都是怎么做的，我是把数据加密后再写入文件的，现在用`Security`库就会方便很多。

首先代码导入



```java
    dependencies {
        implementation "androidx.security:security-crypto:1.0.0-alpha02"
    }
```

`Security 库`主要包含两大类：
 1）**EncryptedFile**
 读写一个加密文件，生成`EncryptedFile`之后，正常打开文件是乱码情况，也就是加密了，需要
 EncryptedFile相关API才能读取。看看怎么实现读写的吧！



```kotlin
    // 写入数据
    fun writeData(context: Context, directory: File) {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val fileToRead = "my_sensitive_data.txt"
        val encryptedFile = EncryptedFile.Builder(
            File(directory, fileToRead),
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val fileContent = "MY SUPER-SECRET INFORMATION"
            .toByteArray(StandardCharsets.UTF_8)
        encryptedFile.openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }
    }
    
    // 读取数据
    fun readData(context: Context, directory: File) {
        // recommended that you use the value specified here.
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val fileToRead = "my_sensitive_data.txt"
        val encryptedFile = EncryptedFile.Builder(
            File(directory, fileToRead),
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = encryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        val plaintext: ByteArray = byteArrayOutputStream.toByteArray()
    }
           
```

2）**EncryptedSharedPreferences**



```kotlin
    val sharedPreferences = EncryptedSharedPreferences
        .create(
        fileName,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val sharedPrefsEditor = sharedPreferences.edit()
    
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Fsecurity%2Fdata)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fsecurity)

#### 测试

测试应用在Android项目中是必不可缺的步骤，包括`功能测试，集成测试，单元测试`。这里主要说的是通过代码的形式编写测试用例，测试应用的的稳定性，完整性等等。

具体体现在Android Studio中有两个测试目录：

- `androidTest目录`应包含在真实或虚拟设备上运行的测试。
- `test 目录`应包含在本地计算机上运行的测试，如单元测试。

具体测试的编写可以看看这个官方项目学习：[testing-samples](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fandroid%2Ftesting-samples)。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Ftesting)

#### TV

`Android TV`应用在国内还是应用比较广泛的，市场上大部分电视都是Android系统，支持APK安装，包括华为鸿蒙系统也支持APK安装了。所以我们手机上的应用基本可以直接安装到电视上，只是UI焦点等方面需要改进。
 以下从四个方面简单说下TV应用的配置，分别是`配置，硬件，按键和测试`。
 1）**配置**
 首先，在Androidmanifest.xml里面声明Activity的时候，如果你想兼容TV版和手机版，可以设置不同的启动Activity，主要表现为设置`android.intent.category.LEANBACK_LAUNCHER`过滤器：



```java
   //手机启动Activity
   <activity
     android:name="com.example.android.MainActivity"
     android:label="@string/app_name" >

     <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
     </intent-filter>
   </activity>
   
   //TV启动Activity
   <activity
     android:name="com.example.android.TvActivity"
     android:label="@string/app_name"
     android:theme="@style/Theme.Leanback">

     <intent-filter>
       <action android:name="android.intent.action.MAIN" />
       <category android:name="android.intent.category.LEANBACK_LAUNCHER" />
     </intent-filter>

   </activity>   
```

2）**硬件**
 硬件主要包括如何判断当前运行环境是TV环境，以及检查TV硬件的某些功能是否存在。



```kotlin
    //判断当前运行环境是TV环境
    val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
    if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
        Log.d(TAG, "Running on a TV Device")
    } else {
        Log.d(TAG, "Running on a non-TV Device")
    }
    
    //检查TV硬件的某些功能是否存在
    // Check if android.hardware.touchscreen feature is available.
    if (packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
        Log.d("HardwareFeatureTest", "Device has a touch screen.")
    }
```

3） **按键**
 TV中的界面事件主要包括：



```java
   BUTTON_B、BACK    返回
   BUTTON_SELECT、BUTTON_A、ENTER、DPAD_CENTER、KEYCODE_NUMPAD_ENTER    选择
   DPAD_UP、DPAD_DOWN、DPAD_LEFT、DPAD_RIGHT   导航
```

按键配置包括：



```java
   nextFocusDown    定义当用户向下导航时下一个获得焦点的视图。
   nextFocusLeft    定义当用户向左导航时下一个获得焦点的视图。
   nextFocusRight   定义当用户向右导航时下一个获得焦点的视图。
   nextFocusUp    定义当用户向上导航时下一个获得焦点的视图。
   
   <TextView android:id="@+id/Category1"
             android:nextFocusDown="@+id/Category2"\>
    
```

4）**测试**
 同样，TV端APP的测试可以直接通过TV模拟器测试，在`AVD Manager`里面创建新的TV 模拟机即可。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftv)

#### Wear OS by Google

Google的手表系统，同样是使用Android开发。国内好像没有基于`Wear OS`的手表，而且据我所知，国外的WearOS设备也很少了，被`WatchOS`全面打败，连Google旗下的App Nest都不支持WearOS了。所以这部分我们了解下就行，有兴趣的可以去看看[官方Demo](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fandroid%2Fwear-os-samples%2Ftree%2Fmaster%2FAlwaysOn)

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fwear)

### Jetpack-架构组件

这个模块的组件就是专门为`MVVM`框架服务的，但是每个库都是可以单独使用的，也是jetpack中比较重要的一大模块。
 简单说下`MVVM`，Model—View—ViewModel。

- `Model层`主要指数据，比如服务器数据，本地数据库数据，所以网络操作和数据库读取就是这一层，只保存数据。
- `View层`主要指UI相关，比如xml布局文件，Activity界面显示
- `ViewModel层`是MVVM的核心，连接view和model，需要将model的数据展示到view上，以及view上的操作数据反映转化到model层，所以就相当于一个双向绑定。

所以就需要，**databinding**进行数据的绑定，单向或者双向。**viewmodel**进行数据管理，绑定view和数据。**lifecycle**进行生命周期管理。**LiveData**进行数据的及时反馈。
 迫不及待了吧，跟随我一起看看每个库的神奇之处。

#### 数据绑定

> 数据绑定库是一种支持库，借助该库，您可以使用声明性格式（而非程序化地）将布局中的界面组件绑定到应用中的数据源。

主要指的就是数据绑定库`DataBinding`，下面从六个方面具体介绍下

配置应用使用数据绑定：

```kotlin
   android {
        ...
        dataBinding {
            enabled = true
        }
    }
    
```

1）**布局和绑定表达式**
 通过数据绑定，我们可以让xml布局文件中的view与数据对象进行绑定和赋值，并且可以借助表达式语言编写表达式来处理视图分派的事件。举个🌰：

```kotlin
    //布局 activity_main.xml
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.name}"/>
    </layout>
    
    //实体类User
    data class User(val name: String)
    
    
    //Activity赋值
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
        binding.user = User("Bob")
    }
    
```

通过`@{}`符号，可以在布局中使用数据对象，并且可以通过DataBindingUtil获取赋值对象。并且`@{}`里面的表达式语言支持多种运算符，包括算术运算符，逻辑运算符等等。

2）**可观察的数据对象**
 可观察性是指一个对象将其数据变化告知其他对象的能力。通过数据绑定库，您可以让对象、字段或集合变为可观察。

比如上文刚说到的User类，我们将name属性改成可观察对象，



```kotlin
   data class User(val name: ObservableField<String>)
   
   val userName = ObservableField<String>()
   userName.set("Bob")

   val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)
   binding.user = User(userName)   
```

然后绑定到布局中，这时候这个User的`name`属性就是被观察对象了，如果`userName`改变，布局里面的`TextView`显示数据也会跟着改变，这就是可观察数据对象。

3）**生成的绑定类**

刚才我们获取绑定布局是通过`DataBindingUtil.setContentView`方法生成ActivityMainBinding对象并绑定布局。那么ActivityMainBinding类是怎么生成的呢？只要你的布局用`layout`属性包围，编译后就会自动生成绑定类，类名称基于布局文件的名称，它会转换为 `Pascal` 大小写形式并在末尾添加 Binding 后缀。

正常创建绑定对象是通过如下写法：



```kotlin
    //Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MyLayoutBinding = MyLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    
    //Fragment
    @Nullable
    fun onCreateView( inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_layout, container, false)
        return mDataBinding.getRoot()
    }
```

4）**绑定适配器**

适配器这里指的是布局中的属性设置，`android:text="@{user.name}"`表达式为例，库会查找接受`user.getName()`所返回类型的`setText(arg)` 方法。
 重要的是，我们可以自定义这个适配器了，也就是布局里面的属性我们可以随便定义它的名字和作用。来个🌰



```kotlin
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }
    
    <ImageView app:imageUrl="@{venue.imageUrl}" />
```

在类中定义一个外部可以访问的方法`loadImage`，注释`@BindingAdapter`里面的属性为你需要定义的属性名称，这里设置的是imageUrl。所以在布局中就可以使用`app:imageUrl`，并传值为String类型，系统就会找到这个适配器方法并执行。

5）**将布局视图绑定到架构组件**
 这一块就是实际应用了，和jetpack其他组件相结合使用，形成完整的`MVVM`分层架构。



```kotlin
        // Obtain the ViewModel component.
        val userModel: UserViewModel by viewModels()

        // Inflate view and obtain an instance of the binding class.
        val binding: ActivityDatabindingMvvmBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_databinding_mvvm)

        // Assign the component to a property in the binding class.
        binding.viewmodel = userModel
        
    <data>
        <variable
            name="viewmodel"
            type="com.panda.jetpackdemo.dataBinding.UserViewModel" />
    </data>
    
    class UserViewModel : ViewModel() {
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        currentName.value="zzz"
    }
}
```

6）**双向数据绑定**

刚才我们介绍的都是单向绑定，也就是布局中view绑定了数据对象，那么如何让数据对象也对view产生绑定呢？也就是`view改变`的时候数据对象也能接收到讯息，形成`双向绑定`。

很简单，比如一个EditText，需求是EditText改变的时候，user对象name数据也会跟着改变，只需要把之前的"@{}"改成"@={}"



```kotlin
    //布局 activity_main.xml
    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <EditText android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@={user.name}"/>
    </layout>
```

很简单吧，同样，这个双向绑定功能也是支持自定义的。来个🌰



```kotlin
object SwipeRefreshLayoutBinding {

    //方法1，数据绑定到view
    @JvmStatic
    @BindingAdapter("app:bind_refreshing")
    fun setSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout,newValue: Boolean) {
        if (swipeRefreshLayout.isRefreshing != newValue)
            swipeRefreshLayout.isRefreshing = newValue
    }

    //方法1，view改变会通知bind_refreshingChanged，并且从该方法获取view的数据
    @JvmStatic
    @InverseBindingAdapter(attribute = "app:bind_refreshing",event = "app:bind_refreshingChanged")
    fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean =swipeRefreshLayout.isRefreshing
            
    //方法3，view如何改变来影响数据内容  
    @JvmStatic
    @BindingAdapter("app:bind_refreshingChanged",requireAll = false)
    fun setOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout,bindingListener: InverseBindingListener?) {
        if (bindingListener != null)
            swipeRefreshLayout.setOnRefreshListener {
                bindingListener.onChange()
            }
    }
}


<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:bind_refreshing="@={viewModel.refreshing }">
</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```

简单说明下，首先通过`bind_refreshing`属性，将数据`viewModel.refreshing`绑定到view上，这样数据变化，view也会跟着变化。然后view变化的时候，通过`InverseBindingAdapter`注释，会调用`bind_refreshingChanged`事件，而bind_refreshingChanged事件告诉了我们view什么时候会进行数据的修改，在这个案例中也就是swipeRefreshLayout下滑的时候会导致数据进行改变，于是数据对象会从`isSwipeRefreshLayoutRefreshing`方法获取到最新的数值，也就是从view更新过来的数据。

这里要注意的一个点是，双向绑定要考虑到死循环问题，当View被改变，数据对象对应发生更新，同时，这个更新又回通知View层去刷新UI，然后view被改变又会导致数据对象更新，无限循环下去了。所以防止死循环的做法就是判断view的数据状态，当发生改变的时候才去更新view。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Fdata-binding)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2FdataBinding)

#### Lifecycles

> 生命周期感知型组件可执行操作来响应另一个组件（如 Activity 和 Fragment）的生命周期状态的变化。这些组件有助于您写出更有条理且往往更精简的代码，这样的代码更易于维护。

`Lifecycles`，称为生命周期感知型组件，可以感知和响应另一个组件（如 Activity 和 Fragment）的生命周期状态的变化。

可能有人会疑惑了，生命周期就那几个，我为啥还要导入一个库呢？有了库难道就不用写生命周期了吗，有什么好处呢？
 举个🌰，让你感受下。

首先导入库，可以根据实际项目情况导入



```kotlin
        // ViewModel
        implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
        // LiveData
        implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
        // Lifecycles only (without ViewModel or LiveData)
        implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
        //.......
```

现在有一个定位监听器，需要在`Activity`启动的时候开启，销毁的时候关闭。正常代码如下：



```kotlin
class BindingActivity : AppCompatActivity() {

    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLocationListener = MyLocationListener(this) { location ->
            // update UI
        }
    }
    public override fun onStart() {
        super.onStart()
        myLocationListener.start()       
    }
    public override fun onStop() {
        super.onStop()
        myLocationListener.stop()
    }

    internal class MyLocationListener(
            private val context: Context,
            private val callback: (Location) -> Unit
    ) {
        fun start() {
            // connect to system location service
        }
        fun stop() {
            // disconnect from system location service
        }
    }
    
}
```

乍一看也没什么问题是吧，但是如果需要管理生命周期的类一多，是不是就不好管理了。所有的类都要在Activity里面管理，还容易漏掉。
 所以解决办法就是实现`解耦`，让需要管理生命周期的类`自己管理`，这样Activity也不会遗漏和臃肿了。上代码：



```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myLocationListener = MyLocationListener(this) { location ->
            // update UI
        }
       lifecycle.addObserver(myLocationListener)
    }



    internal class MyLocationListener (
            private val context: Context,
            private val callback: (Location) -> Unit
    ): LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun start() {

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stop() {
            // disconnect if connected
        }
    }
```

很简单吧，只要实现`LifecycleObserver`接口，就可以用注释的方式执行每个生命周期要执行的方法。然后在Activity里面`addObserver`绑定即可。

同样的，`Lifecycle`也支持自定义生命周期，只要继承LifecycleOwner即可，然后通过`markState`方法设定自己类的生命周期，举个🌰

```kotlin
class BindingActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    public override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }
}    
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Flifecycle)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Flifecycle)

#### LiveData

> LiveData 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。

`LiveData` 是一种可观察的数据存储器类。
 等等，这个介绍好像似曾相识？对，前面说数据绑定的时候就有一个可观察的数据对象`ObservableField`。那两者有什么区别呢？

1）`LiveData` 具有生命周期感知能力，可以感知到Activity等的生命周期。这样有什么好处呢？很常见的一点就是可以减少内存泄漏和崩溃情况了呀，想想以前你的项目中针对网络接口返回数据的时候都要判断当前界面是否销毁，现在LiveData就帮你解决了这个问题。

具体为什么能解决崩溃和泄漏问题呢？

- `不会发生内存泄漏`
   观察者会绑定到 Lifecycle 对象，并在其关联的生命周期遭到销毁后进行自我清理。
- `不会因 Activity 停止而导致崩溃`
   如果观察者的生命周期处于非活跃状态（如返回栈中的 Activity），则它不会接收任何 LiveData 事件。
- `自动判断生命周期并回调方法`
   如果观察者的生命周期处于 STARTED 或 RESUMED状态，则 LiveData 会认为该观察者处于活跃状态，就会调用onActive方法，否则，如果 LiveData 对象没有任何活跃观察者时，会调用 onInactive()方法。

2） LiveData更新数据更灵活，不一定是改变数据，而是调用方法`（postValue或者setValue）`的方式进行UI更新或者其他操作。

好了。还是举个🌰更直观的看看吧：



```kotlin
    //导入库：
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"

    class StockLiveData(symbol: String) : LiveData<BigDecimal>() {
        private val stockManager = StockManager(symbol)

        private val listener = { price: BigDecimal ->
            value = price
        }

        override fun onActive() {
            stockManager.requestPriceUpdates(listener)
        }

        override fun onInactive() {
            stockManager.removeUpdates(listener)
        }
    }
    
    public class MyFragment : Fragment() {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val myPriceListener: LiveData<BigDecimal> = StockLiveData("")
            myPriceListener.observe(this, Observer<BigDecimal> { price: BigDecimal? ->
                // 监听livedata的数据变化，如果调用了setValue或者postValue会调用该onChanged方法
                //更新UI数据或者其他处理
            })
        }
    }
        
```

这是一个股票数据对象，`StockManager`为股票管理器，如果该对象有活跃观察者时，就去监听股票市场的情况，如果没有活跃观察者时，就可以断开监听。
 当监听到股票信息变化，该股票数据对象就会通过`setValue`方法进行数据更新，反应到观察者的onChanged方法。这里要注意的是`setValue`方法只能在主线程调用，而`postValue`则是在其他线程调用。
 当`Fragment`这个观察者生命周期发生变化时，`LiveData`就会移除这个观察者，不再发送消息，所以也就避免崩溃问题。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Flivedata)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Flivedata)

#### Navigation

> 导航
>  Navigation 组件旨在用于具有一个主 Activity 和多个 Fragment 目的地的应用。主 Activity 与导航图相关联，且包含一个负责根据需要交换目的地的 NavHostFragment。在具有多个 Activity 目的地的应用中，每个 Activity 均拥有其自己的导航图。

所以说白了，`Navigation`就是一个`Fragment`的管理框架。
 怎么实现？创建Activity，Fragment，进行连接。

1）**导入库**



```kotlin
  def nav_version = "2.3.0"
  implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
  implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
```

2）**创建3个Fragment和一个Activity**

3）**创建res/navigation/my_nav.xml 文件**



```kotlin
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    app:startDestination="@id/myFragment1"
    tools:ignore="UnusedNavigation">

    <fragment
        android:id="@+id/myFragment1"
        android:name="com.example.studynote.blog.jetpack.navigation.MyFragment1"
        android:label="fragment_blank"
        tools:layout="@layout/fragmetn_my_1" >
        <action
            android:id="@+id/action_blankFragment_to_blankFragment2"
            app:destination="@id/myFragment2" />
    </fragment>

    <fragment
        android:id="@+id/myFragment2"
        android:name="com.example.studynote.blog.jetpack.navigation.MyFragment1"
        android:label="fragment_blank"
        tools:layout="@layout/fragmetn_my_1" >
        <action
            android:id="@+id/action_blankFragment_to_blankFragment2"
            app:destination="@id/myFragment3" />
    </fragment>

    <fragment
        android:id="@+id/myFragment3"
        android:name="com.example.studynote.blog.jetpack.navigation.MyFragment1"
        android:label="fragment_blank"
        tools:layout="@layout/fragmetn_my_1" >
    </fragment>
</navigation>
```

在res文件夹下新建`navigation`目录，并新建`my_nav.xml` 文件。配置好每个Fragment，其中：

- `app:startDestination` 属性代表一开始显示的fragment
- `android:name` 属性代表对应的Fragment路径
- `action` 代表该Fragment存在的跳转事件，比如myFragment1可以跳转myFragment2。

1. **修改Activity的布局文件：**



```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:id="@+id/container"
android:layout_width="match_parent"
android:layout_height="match_parent">

<fragment
    android:id="@+id/nav_host_fragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:defaultNavHost="true"
    app:navGraph="@navigation/my_nav" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

可以看到，Activity的布局文件就是一个fragment控件，name为NavHostFragment，`navGraph`为刚才新建的mynavigation文件。

5）**配置完了之后，就可以设置具体的跳转逻辑了。**



```kotlin
    override fun onClick(v: View) {
    //不带参数
 v.findNavController().navigate(R.id.action_blankFragment_to_blankFragment2)
   //带参数
    var bundle = bundleOf("amount" to amount)
    v.findNavController().navigate(R.id.confirmationAction, bundle)
 
    }
    
    //接收数据
    tv.text = arguments?.getString("amount")
    
```

需要注意的是，跳转这块官方建议用`Safe Args` 的Gradle 插件，该插件可以生成简单的 `object 和 builder`类，以便以类型安全的方式浏览和访问任何关联的参数。这里就不细说了，感兴趣的可以去[官网看看](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Fnavigation%2Fnavigation-getting-started%23ensure_type-safety_by_using_safe_args)

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Fnavigation%2F)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fnavigation)

#### Room

> Room 持久性库在 SQLite 的基础上提供了一个抽象层，让用户能够在充分利用 SQLite 的强大功能的同时，获享更强健的数据库访问机制。

所以`Room`就是一个数据库框架。问题来了，市面上那么多数据库组件，比如`ormLite，greendao`等等，为什么google还要出一个room，有什么优势呢？

- **性能优势**，一次数据库操作主要包括：构造sql语句—编译语句—传入参数—执行操作。`ORMLite`主要在获取参数属性值的时候，是通过反射获取的，所以速度较慢。`GreenDao`在构造sql语句的时候是通过代码拼接，所以较慢。`Room`是通过接口方法的注解生成sql语句，也就是编译成字节码的时候就生成了sql语句，所以运行起来较快。
- **支持jetpack其他组件（比如LiveData，Paging）以及RxJava**，这就好比借助了当前所在的优势环境，就能给你带来一些得天独厚的优势。当然实际使用起来也确实要方便很多，比如`liveData`结合，就能在数据查询后进行自动UI更新。

既然Room这么优秀，那就用起来吧。
 Room的接入主要有三大点：`DataBase、Entity、Dao`。分别对应数据库，表和数据访问。

1）**首先导入库：**



```kotlin
    apply plugin: 'kotlin-kapt'

    dependencies {
      def room_version = "2.2.5"

      implementation "androidx.room:room-runtime:$room_version"
      kapt "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor

      // optional - Kotlin Extensions and Coroutines support for Room
      implementation "androidx.room:room-ktx:$room_version"

      // optional - RxJava support for Room
      implementation "androidx.room:room-rxjava2:$room_version"
    }
    
```

2）**建立数据库类，声明数据库表成员，数据库名称，数据库版本，单例等等**



```kotlin
@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDb? = null

        @Synchronized
        fun get(context: Context): UserDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    UserDb::class.java, "StudentDatabase").build()
            }
            return instance!!
        }
    }
}
```

3）**建表，可以设置主键，外键，索引，自增等等**



```kotlin
@Entity
data class User(@PrimaryKey(autoGenerate = true) val id: Int,
                val name: String)
```

4）**Dao，数据操作**



```kotlin
@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM User")
    fun getAllUser2(): LiveData<List<User>>

    @Query("SELECT * from user")
    fun getAllUser3(): Flowable<List<User>>

    @Insert
    fun insert(users: List<User>)
}
```

然后就可以进行数据库操作了，很简单吧。
 [官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Froom)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fpaging_room)

#### Paging

> 分页库可帮助您一次加载和显示一小块数据。按需载入部分数据会减少网络带宽和系统资源的使用量。

所以`Paging`就是一个分页库，主要用于Recycleview列表展示。下面我就结合Room说说Paging的用法。
 使用Paging主要注意两个类：`PagedList和PagedListAdapter`。
 1）**PagedList**
 用于加载应用数据块，绑定数据列表，设置数据页等。结合上述`Room`的Demo我继续写了一个`UserModel`进行数据管理：



```kotlin
class UserModel(app: Application) : AndroidViewModel(app) {
    val dao = UserDb.get(app).userDao()
    var idNum = 1

    companion object {
        private const val PAGE_SIZE = 10
    }

    //初始化PagedList
    val users = LivePagedListBuilder(
        dao.getAllUser(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    ).build()

    //插入用户
    fun insert() = ioThread {
        dao.insert(newTenUser())
    }

    //获取新的10个用户
    fun newTenUser(): ArrayList<User> {
        var newUsers = ArrayList<User>()
        for (index in 1..10) {
            newUsers.add(User(0, "bob${++idNum}"))
        }
        return newUsers
    }

}
```

2）**PagedListAdapter**
 使用Recycleview必要要用到adatper，所以这里需要绑定一个继承自`PagedListAdapter`的adapter：



```kotlin
class UserAdapter : PagedListAdapter<User, UserAdapter.UserViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(parent)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }

    class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) {

        private val tv1 = itemView.findViewById<TextView>(R.id.name)
        var user: User? = null

        fun bindTo(user: User?) {
            this.user = user
            tv1.text = user?.name
        }
    }
}
```

这里还用到了`DiffUtil.ItemCallback` 类，用于比较数据，进行数据更新用。

ok，数据源，adapter都设置好了，接下来就是监听数据，刷新数据就可以了



```kotlin
        // 监听users数据，数据改变调用submitList方法
        viewModel.users.observe(this, Observer(adapter::submitList))
```

对，就是这么一句，监听`PagedList`，并且在它改变的时候调用PagedListAdapter的`submitList`方法。
 这分层够爽吧，其实这也就是paging或者说jetpack给我们项目带来的优势，层层解耦，adapter都不用维护list数据源了。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Fpaging)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fpaging_room)

#### ViewModel

> ViewModel 类旨在以注重生命周期的方式存储和管理界面相关的数据。ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。

终于说到`ViewModel`了，其实之前的demo都用了好多遍了，`ViewModel`主要是从界面控制器逻辑中分离出视图数据，为什么要这么做呢？主要为了解决两大问题：

- 以前Activity中如果被系统销毁或者需要重新创建的时候，页面临时性数据都会丢失，需要通过`onSaveInstanceState()` 方法保存，onCreate方法中读取。而且数据量一大就更加不方便了。
- 在Activity中，难免有些异步调用，所以就会容易导致界面销毁时候，这些调用还存在。那就会发生内存泄漏或者直接崩溃。

所以`ViewModel`诞生了，还是解耦，我把数据单独拿出来管理，还加上生命周期，那不就可以解决这些问题了吗。而且当所有者 Activity 完全销毁之后，`ViewModel`会调用其`onCleared()`方法，以便清理资源。

接下来举个🌰，看看ViewModel具体是怎么使用的：



```kotlin
def lifecycle_version = "2.2.0"
// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"


class SharedViewModel : ViewModel() {
    var userData = MutableLiveData<User>()

    fun select(item: User) {
        userData.value = item
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class MyFragment1 : Fragment() {
    private lateinit var btn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model=activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        btn.setOnClickListener{
            model?.select(User(0,"bob"))
        }
    }
}

class MyFragment2 : Fragment() {
    private lateinit var btn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model=activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        model?.userData?.observe(viewLifecycleOwner, Observer<User> { item ->
            // Update the UI
        })
    }
}
    
```

Fragment中，获取到`viewmodel`的实例，然后进行数据监听等操作。等等，你能发现什么不？
 对了，数据通信。不同的 Fragment 可以使用其父Activity共享`ViewModel` 来进行数据的通信，厉害吧。还有很多其他的用法，去项目中慢慢发现吧！

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Fviewmodel)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fviewmodel)

#### WorkManager

> 使用 WorkManager API 可以轻松地调度即使在应用退出或设备重启时仍应运行的可延迟异步任务。

听听这个介绍就很神奇了，应用退出和设备重启都能自动运行？通过广播？那数据又是怎么保存的呢？听说还可以执行周期性异步任务，顺序链式调用哦！接下来一一解密

- 关于应用退出和设备重启
   如果APP正在运行，`WorkManager`会在APP进程中起一个新线程来运行任务；如果APP没有运行，`WorkManager`会选择一个合适的方式来调度后台任务--根据系统级别和APP状态，WorkManager可能会使用`JobScheduler，FireBase JobDispatcher`或者`AlarmManager`。
- 关于数据保存
   `WorkManager`创建的任务数据都会保存到数据库，用的是`Room`框架。然后重启等时间段都会去数据库寻找需要安排执行的任务，然后判断`约束条件`，满足即可执行。

一般这个API应用到什么场景呢？想想，可靠运行，还可以周期异步。
 对了，发送日志。可以通过`WorkManager`设定周期任务，每天执行一次发送日志的任务。而且能够保证你的任务可靠运行，一定可以上传到，当然也是支持监听任务结果等。🌰：

1）**导入库**



```kotlin
    dependencies {
      def work_version = "2.3.4"
        // Kotlin + coroutines
        implementation "androidx.work:work-runtime-ktx:$work_version"

        // optional - RxJava2 support
        implementation "androidx.work:work-rxjava2:$work_version"

        // optional - GCMNetworkManager support
        implementation "androidx.work:work-gcm:$work_version"
      }
    
```

2） **新建任务类**，继承`Worker`，重写`doWork`方法，返回任务结果。



```kotlin
class UploadLogcatWork(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        if (isUploadLogcatSuc()) {
            return Result.success()
        } else if (isNeedRetry()){
            return Result.retry()
        }

        return Result.failure()
    }

    fun isUploadLogcatSuc(): Boolean {
        var isSuc: Boolean = false
        return isSuc
    }

    fun isNeedRetry(): Boolean {
        var isSuc: Boolean = false
        return isSuc
    }
}
```

3）**最后就是设定约束**（是否需要网络，是否支持低电量，是否支持充电执行，延迟等等），执行任务（单次任务或者循环周期任务）



```kotlin
        //设定约束
        val constraints =
            Constraints.Builder()
                //网络链接的时候使用
                .setRequiredNetworkType(NetworkType.CONNECTED)
                //是否在设备空闲的时候执行
                .setRequiresDeviceIdle(false)
                //是否在低电量的时候执行
                .setRequiresBatteryNotLow(true)
                //是否在内存不足的时候执行
                .setRequiresStorageNotLow(true)
                //是否时充电的时候执行
                .setRequiresCharging(true)
                //延迟执行
                .setTriggerContentMaxDelay(1000 * 1, TimeUnit.MILLISECONDS)
                .build()

        //设定循环任务
        val uploadRequest =
            PeriodicWorkRequestBuilder<UploadLogcatWork>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag("uploadTag")
                .build()

        //执行
        WorkManager.getInstance(applicationContext).enqueue(uploadRequest)


        //监听执行结果
        WorkManager.getInstance(this)
//            .getWorkInfosByTagLiveData("uploadTag") //通过tag拿到work
            .getWorkInfoByIdLiveData(uploadRequest.id) //通过id拿到work
            .observe(this, Observer {
                it?.apply {
                    when (this.state) {
                        WorkInfo.State.BLOCKED -> println("BLOCKED")
                        WorkInfo.State.CANCELLED -> println("CANCELLED")
                        WorkInfo.State.RUNNING -> println("RUNNING")
                        WorkInfo.State.ENQUEUED -> println("ENQUEUED")
                        WorkInfo.State.FAILED -> println("FAILED")
                        WorkInfo.State.SUCCEEDED -> println("SUCCEEDED")
                        else -> println("else status ${this.state}")
                    }
                }

            })
```

4）**另外还支持任务取消，任务链式顺序调用等**



```kotlin
    //取消
    fun cancelWork(){
  WorkManager.getInstance(applicationContext).cancelAllWorkByTag("uploadTag")
    }

    fun startLineWork(){
        //图片滤镜1
        val filter1 = OneTimeWorkRequestBuilder<UploadLogcatWork>()
            .build()
        //图片滤镜2
        val filter2 = OneTimeWorkRequestBuilder<UploadLogcatWork>()
            .build()
        //图片压缩
        val compress = OneTimeWorkRequestBuilder<UploadLogcatWork>()
            .build()
        //图片上传
        val upload = OneTimeWorkRequestBuilder<UploadLogcatWork>()
            .build()

        WorkManager.getInstance(applicationContext)
            .beginWith(listOf(filter1, filter2))
            .then(compress)
            .then(upload)
            .enqueue()
    }
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftopic%2Flibraries%2Farchitecture%2Fworkmanager)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fworkmanager)

### Jetpack-行为组件

#### CameraX

> CameraX 是一个 Jetpack 支持库，旨在帮助您简化相机应用的开发工作。它提供一致且易于使用的 API Surface，适用于大多数 Android 设备，并可向后兼容至 Android 5.0（API 级别 21）。
>  虽然它利用的是 camera2 的功能，但使用的是更为简单且基于用例的方法，该方法具有生命周期感知能力。它还解决了设备兼容性问题，因此您无需在代码库中添加设备专属代码。这些功能减少了将相机功能添加到应用时需要编写的代码量。

想必大家都了解过`Camera API`和`Camera2 API`，总结就是两个字，不好用。哈哈，自我感觉，在我印象中，我要照相拍一张照片，不是应该直接调用一句代码可以完成吗。但是用之前的API，我需要去管理相机实例，设置SufraceView相关的各种东西，还有预览尺寸和图像尺寸，处理设置各种监听等等，头已晕。

可能是官方听到了我的抱怨，于是`CameraX`来了，CameraX是基于`camera2`进行了封装，给我们提供了更简单的解决方案来解决我们之前的困境。🌰来了



```kotlin
    // CameraX core library using the camera2 implementation
    def camerax_version = "1.0.0-beta06"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation "androidx.camera:camera-core:${camerax_version}"
    implementation "androidx.camera:camera-camera2:${camerax_version}"
    // If you want to additionally use the CameraX Lifecycle library
    implementation "androidx.camera:camera-lifecycle:${camerax_version}"
    // If you want to additionally use the CameraX View class
    implementation "androidx.camera:camera-view:1.0.0-alpha13"
    // If you want to additionally use the CameraX Extensions library
    implementation "androidx.camera:camera-extensions:1.0.0-alpha13"
    
    
    <uses-permission android:name="android.permission.CAMERA" />
    
    //初始化相机
    private fun initCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            try {
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()


                //图片拍摄用例
                mImageCapture = ImageCapture.Builder()
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .build()

                //配置参数（后置摄像头等）
                // Choose the camera by requiring a lens facing
                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build()

                //指定要与相机关联的生命周期，该生命周期会告知 CameraX 何时配置相机拍摄会话并确保相机状态随生命周期的转换相应地更改。
                val camera: Camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    mImageCapture
                )

                //相机预览
                preview.setSurfaceProvider(view_finder.createSurfaceProvider())

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    //拍照并保存
    fun takePhoto(view: View?) {
        if (mImageCapture != null) {
            val outputFileOptions: OutputFileOptions = OutputFileOptions.Builder(cretaeFile()).build()

            //拍照
            mImageCapture?.takePicture(
                outputFileOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(@NonNull outputFileResults: OutputFileResults) {
                        //保存成功
                        Log.e(TAG, "success")
                    }

                    override fun onError(@NonNull exception: ImageCaptureException) {
                        //保存失败
                        Log.e(TAG, "fail")
                    }
                })
        }
    }    
```

使用起来挺方便吧，而且可以绑定当前activity的生命周期，这就涉及到另外一个组件`Lifecycle`了，通过一次绑定事件，就可以使相机状态随生命周期的转换相应地更改。
 另外要注意的是先获取相机权限哦。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fcamerax)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fcamerax)

#### 下载管理器

> DownloadManager下载管理器是一个处理长时间运行的HTTP下载的系统服务。客户端可以请求将URI下载到特定的目标文件。下载管理器将在后台执行下载，负责HTTP交互，并在失败或跨连接更改和系统重启后重试下载。

`DownloadManager`，大家应该都很熟悉吧，`android2.3`就开通提供的API，很方便就可以下载文件，包括可以设置是否通知显示，下载文件夹名，文件名，下载进度状态查询等等。🌰来



```kotlin
class DownloadActivity : AppCompatActivity() {

    private var mDownId: Long = 0
    private var mDownloadManager: DownloadManager? = null
    private val observer: DownloadContentObserver = DownloadContentObserver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //配置下载参数，enqueue开始下载
    fun download(url: String) {
        mDownloadManager =
            this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        // 设置文件夹文件名
        request.setDestinationInExternalPublicDir("lz_download", "test.apk")
        // 设置允许的网络类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
        // 文件类型
        request.setMimeType("application/zip")
        // 设置通知是否显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //设置通知栏标题
        request.setTitle("apk download")
        //设置通知栏内容
        request.setDescription("*** apk")

        mDownId = mDownloadManager!!.enqueue(request)

 contentResolver.registerContentObserver(mDownloadManager!!.getUriForDownloadedFile(mDownId), true, observer)
    }

    //通过ContentProvider查询下载情况
    fun queryDownloadStatus(){
        val query = DownloadManager.Query()
        //通过下载的id查找
        //通过下载的id查找
        query.setFilterById(mDownId)
        val cursor: Cursor = mDownloadManager!!.query(query)
        if (cursor.moveToFirst()) {
            // 已下载字节数
            val downloadBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            // 总字节数
            val allBytes= cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            // 状态
            when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                DownloadManager.STATUS_PAUSED -> {
                }
                DownloadManager.STATUS_PENDING -> {
                }
                DownloadManager.STATUS_RUNNING -> {
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    cursor.close()
                }
                DownloadManager.STATUS_FAILED -> {
                    cursor.close()
                }
            }

        }
    }

    //取消下载，删除文件
    fun unDownLoad(view: View?) {
        mDownloadManager!!.remove(mDownId)
    }


    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(observer)
    }


    //监听下载情况
    inner class DownloadContentObserver : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            queryDownloadStatus()
        }
    }

}
```

demo应该写的很清楚了，要注意的就是保存下载id，后续取消下载，查询下载进度状态都是通过这个id来查询。监听下载进度主要是通过观察`getUriForDownloadedFile`方法返回的uri，观察这个uri指向的数据库变化来获取进度。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Freference%2Fandroid%2Fapp%2FDownloadManager)         
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fdownloadmanager)

#### 媒体和播放

> Android 多媒体框架支持播放各种常见媒体类型，以便您轻松地将音频、视频和图片集成到应用中。

这里媒体和播放指的是音频视频相关内容，主要涉及到两个相关类：

- `MediaPlayer`
- `ExoPlayer`

`MediaPlayer`不用说了，应该所有人都用过吧，待会就顺便提一嘴。
 `ExoPlayer`是一个单独的库，也是google开源的媒体播放器项目，听说是Youtube APP所使用的播放器，所以他的功能也是要比`MediaPlayer`强大，支持各种自定义，可以与`IJKPlayer`媲美，只是使用起来比较复杂。

1）**MediaPlayer**



```kotlin
        //播放本地文件
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.test_media)
        mediaPlayer?.start()

        //设置播放不息屏 配合权限WAKE_LOCK使用
        mediaPlayer?.setScreenOnWhilePlaying(true)


        //播放本地本地可用的 URI
        val myUri: Uri = Uri.EMPTY
        val mediaPlayer2: MediaPlayer? = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(applicationContext, myUri)
            prepare()
            start()
        }

        //播放网络文件
        val url = "http://........"
        val mediaPlayer3: MediaPlayer? = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            prepare()
            start()
        }


        //释放
        mediaPlayer?.release()
        mediaPlayer = null
    
```

2）**ExoPlayer**



```kotlin
   compile 'com.google.android.exoplayer:exoplayer:r2.X.X'
   
    var player: SimpleExoPlayer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exoplayer)

        //初始化
        player = SimpleExoPlayer.Builder(this).build()
        video_view.player = player
        player?.playWhenReady = true

        //设置播放资源
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, "yourApplicationName")
        )
        val uri: Uri = Uri.EMPTY
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
        player?.prepare(videoSource)
    }

    private fun releasePlayer() {
        //释放
        player?.release()
        player = null
    }
```

好像也不复杂？哈哈，更强大的功能需要你去发现。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fmedia-apps%2Fmedia-apps-overview)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fmedia)

#### 通知

> 通知是指 Android 在应用的界面之外显示的消息，旨在向用户提供提醒、来自他人的通信信息或应用中的其他实时信息。用户可以点按通知来打开应用，也可以直接在通知中执行某项操作。

这个应该都了解，直接上个🌰



```kotlin
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "mychannel"
            val descriptionText = "for test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(){
        val intent = Intent(this, SettingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }

    }
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fui%2Fnotifiers%2Fnotifications)

#### 权限

> 权限的作用是保护 Android 用户的隐私。Android 应用必须请求权限才能访问敏感的用户数据（例如联系人和短信）以及某些系统功能（例如相机和互联网）。系统可能会自动授予权限，也可能会提示用户批准请求，具体取决于访问的功能。

权限大家应该也都很熟悉了。

- 危险权限。6.0以后使用危险权限需要申请，推荐[RxPermissions库](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Ftbruyelle%2FRxPermissions)
- 可选硬件功能的权限。 对于使用硬件的应用，比如使用了相机，如果你想让`Google Play`允许将你的应用安装在没有该功能的设备上，就要配置硬件功能的权限为不必须的：<uses-feature android:name="android.hardware.camera" android:required="false" />
- 自定义权限。这个可能有些同学没接触过，我们知道，如果我们设置Activity的`exported`属性为true，别人就能通过包名和Activity名访问我们的Activty，那如果我们又不想让所有人都能访问我这个Activty呢?可以通过`自定义权限`实现。🌰来



```kotlin
//应用A
<manifest
  xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.test.myapp" >
    
    <permission
      android:name="com.test.myapp.permission.DEADLY_ACTIVITY"
      android:permissionGroup="android.permission-group.COST_MONEY"
      android:protectionLevel="dangerous" />
    
     <activity
            android:name="MainActivity"
            android:exported="true" 
            android:permission="com.test.myapp.permission.DEADLY_ACTIVITY">
       </activity>
</manifest>

//应用B
<manifest
  xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.test.otherapp" >
    
    <uses-permission android:name="com.test.myapp.permission.DEADLY_ACTIVITY" />
</manifest>
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fpermissions)

#### 偏好设置

> 建议使用 AndroidX Preference Library 将用户可配置设置集成至您的应用中。此库管理界面，并与存储空间交互，因此您只需定义用户可以配置的单独设置。此库自带 Material 主题，可在不同的设备和操作系统版本之间提供一致的用户体验。

开始看到这个标题我是懵逼的，设置？我的设置页官方都可以帮我写了？然后我就去研究了`Preference库`，嘿，还真是，如果你的App本身就是`Material风格`，就可以直接用这个了。但是也正是由于风格固定，在实际多样的APP中应用比较少。
 来个🌰



```kotlin
   implementation 'androidx.preference:preference:1.1.0-alpha04'
   
   //res-xml-setting.xml
   <?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <PreferenceCategory
        app:key="notifications_category"
        app:title="Notifications">
        <SwitchPreferenceCompat
            app:key="notifications"
            app:title="Enable message notifications" />
    </PreferenceCategory>

    <PreferenceCategory
        app:key="help_category"
        app:title="Help">
        <Preference
            app:key="feedback"
            app:summary="Report technical issues or suggest new features"
            app:title="Send feedback" />

        <Preference
            app:key="webpage"
            app:title="View webpage">
            <intent
                android:action="android.intent.action.VIEW"
                android:data="http://www.baidu.com" />
        </Preference>
    </PreferenceCategory>
</PreferenceScreen>


class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        val feedbackPreference: Preference? = findPreference("feedback")

        feedbackPreference?.setOnPreferenceClickListener {
            Toast.makeText(context,"hello Setting",Toast.LENGTH_SHORT).show()
            true
        }
    }
}


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingFragment())
            .commit()
    }
    
}
   
```

首先新建`xml`文件，也就相当于设置页的布局了，包括那些分类，那些选项，以及选项的功能。
 然后新建`fragment`继承自`PreferenceFragmentCompat`，这里就可以绑定xml文件，并且可以设置点击事件。
 最后将fragment加到Activity即可。✌️

来张效果图看看



![img](https:////upload-images.jianshu.io/upload_images/8690467-e32f004824488ce3.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1080/format/webp)

jetpack-setting.jpg

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fui%2Fsettings)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fsetting)

#### 共享

> Android 应用的一大优点是它们能够互相通信和集成。如果某一功能并非应用的核心，而且已存在于另一个应用中，为何要重新开发它？

这里的共享主要指的是`应用间的共享`，比如发邮件功能，打开网页功能，这些我们都可以直接调用系统应用或者其他三方应用来帮助我们完成这些功能，这也就是共享的意义。



```kotlin
    //发送方
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
    
    //接收方
    <activity android:name=".ui.MyActivity" >
        <intent-filter>
            <action android:name="android.intent.action.SEND" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="text/plain" />
        </intent-filter>
    </activity>
        
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fsharing)

#### 切片

> 切片是界面模板，可以在 Google 搜索应用中以及 Google 助理中等其他位置显示您应用中的丰富而动态的互动内容。切片支持全屏应用体验之外的互动，可以帮助用户更快地执行任务。您可以将切片构建成为应用操作的增强功能。

这个介绍确实有点模糊，但是说到`Slice`你会不会有点印象？2018年Google I/0宣布推出新的界面操作`Action & Slice`。而这个Slice就是这里说的切片。他能做什么呢？可以让使用者能快速使用到 app 里的某个特定功能。只要开发者导入 Slice 功能，使用者在使用搜寻、Google Play 商店、Google Assitant或其他内建功能时都会出现 `Slice` 的操作建议。

说白了就是你的应用一些功能可以在其他的应用显示和操作。

所以，如果你的应用发布在`GooglePlay`的话，还是可以了解学习下Slice相关内容，毕竟是Google为了应用轻便性做出的又一步实验。

怎么开发这个功能呢？很简单，只需要一步，右键`New—other—Slice Provider`就可以了。
 slice库，provider和SliceProvider类都配置好了，方便吧。贴下代码：



```kotlin
     <provider
          android:name=".slice.MySliceProvider"
          android:authorities="com.panda.jetpackdemo.slice"
          android:exported="true">
          <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.app.slice.category.SLICE" />
                <data
                    android:host="panda.com"
                    android:pathPrefix="/"
                    android:scheme="http" />
            </intent-filter>
        </provider>
        
        
class MySliceProvider : SliceProvider() {
    /**
     * Construct the Slice and bind data if available.
     * 切片匹配
     */
    override fun onBindSlice(sliceUri: Uri): Slice? {
        val context = context ?: return null
        val activityAction = createActivityAction() ?: return null
        return if (sliceUri.path.equals("/hello") ) {
            Log.e("lz6","222")
            ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                .addRow(
                    ListBuilder.RowBuilder()
                        .setTitle("Hello World")
                        .setPrimaryAction(activityAction)
                )
                .build()
        } else {
            // Error: Path not found.
            ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                .addRow(
                    ListBuilder.RowBuilder()
                        .setTitle("URI not found.")
                        .setPrimaryAction(activityAction)
                )
                .build()
        }
    }

    //切片点击事件
    private fun createActivityAction(): SliceAction? {
        return SliceAction.create(
            PendingIntent.getActivity(
                context, 0, Intent(context, SettingActivity::class.java), 0
            ),
            IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
            ListBuilder.ICON_IMAGE,
            "Open App"
        )
    }

}
        
```

如上就是切片的重要代码，其中`onBindSlice`是用来匹配uri的，比如上述如果uri为/hello就显示一个ListBuilder。`createActivityAction`方法则是响应切片点击事件的。
 可以看到在AndroidManifest.xml中是通过`provider`配置的，所以这个切片的原理就是通过`ContentProvider`形式，让外部可以访问这个provider，然后响应相关事件或者显示相关的view。

好了，接下来就是测试切片使用了，完整的切片URI是`slice-content://{authorities}/{action}`，所以这里对应的就是`slice-content://com.panda.jetpackdemo.slice/hello`。

又在哪里可以使用呢？官方提供了一个可供测试的app—[slice-viewer](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fandroid%2Fuser-interface-samples%2Freleases)。
 下载下来后，配置好URI，就会提示要访问某某应用的切片权限提示，点击确定就可以看到切片内容了（注意最好使用模拟器测试，真机有可能无法弹出切片权限弹窗）。如下图，点击hello就可以跳转到我们之前`createActivityAction`方法里面设置的Activity了。

![img](https:////upload-images.jianshu.io/upload_images/8690467-3cf49b0c9aa098bc.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/385/format/webp)

slice.jpg

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Fslices)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fslice)

### Jetpack-界面组件

#### 动画和过渡

> 当界面因响应用户操作而发生变化时，您应为布局过渡添加动画。这些动画可向用户提供有关其操作的反馈，并有助于让用户始终关注界面。

动画也是老生常谈的内容了。说到动画，我们都会想到`帧动画，属性动画，补间动画`等等。今天我们从不一样的角度归类一些那些你熟悉又不熟悉的动画。

1）**为位图添加动画**

- `AnimationDrawable`。接连加载一系列可绘制资源以创建动画。即属性动画，通过设置每帧的图像，形成动画。
- `AnimatedVectorDrawable`。为矢量可绘制对象的属性添加动画效果，例如旋转或更改路径数据以将其变为其他图片。

其中主要讲下`AnimatedVectorDrawable，VectorDrawable`是为了支持SVG而生，SVG 是可缩放矢量图形，用xml代码描绘图像。下面举个🌰



```kotlin
//res-drawable-vectordrawable.xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:height="64dp"
    android:width="64dp"
    android:viewportHeight="600"
    android:viewportWidth="600">
    <group
        android:name="rotationGroup"
        android:pivotX="300.0"
        android:pivotY="300.0"
        android:rotation="45.0" >
        <path
            android:name="v"
            android:fillColor="#000000"
            android:pathData="M300,70 l 0,-70 70,70 0,0 -70,70z" />
    </group>
</vector>

//res-animator-path_morph.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <objectAnimator
        android:duration="3000"
        android:propertyName="pathData"
        android:valueFrom="M300,70 l 0,-70 70,70 0,0   -70,70z"
        android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0 z"
        android:valueType="pathType" />
</set>

//res-animator-rotation.xml
<?xml version="1.0" encoding="utf-8"?>
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="6000"
    android:propertyName="rotation"
    android:valueFrom="0"
    android:valueTo="360" />


//利用上面两个动画文件和一个SVG图像，生成animated-vector可执行动画
//res-drawable-animatiorvectordrawable.xml
<?xml version="1.0" encoding="utf-8"?>
<animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:drawable="@drawable/vectordrawable" >
    <target
        android:name="rotationGroup"
        android:animation="@animator/rotation" />
    <target
        android:name="v"
        android:animation="@animator/path_morph" />
</animated-vector>


//布局文件activity_vector.xml
    <ImageView
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:srcCompat="@drawable/animatorvectordrawable"
        app:layout_constraintTop_toTopOf="parent"
        />
        
//activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector)
        imageView.setOnClickListener {
            (imageView.drawable as Animatable).start()
        }
    }
```

ok，运行后，点击图像，就会发现一个绕圈的同时又会自变的动画了，感觉有点像地球自转和公转，感兴趣的同学可以自己实现下。

2）**为界面可见性和动作添加动画**
 这一部分主要就是属性动画。属性动画的原理就是在一段时间内更新 View 对象的属性，并随着属性的变化不断地重新绘制视图。也就是`ValueAnimator`，以及在此技术上衍生的`ViewPropertyAnimator` 和 `ObjectAnimator`。主要运用到控件本身的基础动画以及自定义view动画。

3）**基于物理特性的动作**
 这部分可以让动画应尽可能运用现实世界的物理定律，以使其看起来更自然。比如弹簧动画和投掷动画。这里举个弹簧动画的🌰



```kotlin
    def dynamicanimation_version = "1.0.0"
    implementation "androidx.dynamicanimation:dynamicanimation:$dynamicanimation_version"

        val springForce = SpringForce(0.0f)
            .setDampingRatio(0f)  //设置阻尼
            .setStiffness(0.5f)  //设置刚度

        imageView2.setOnClickListener {
            SpringAnimation(imageView2, DynamicAnimation.TRANSLATION_Y).apply {
                spring = springForce
                setStartVelocity(500f) //设置速度
                start()
            }
        }
```

4）**为布局更改添加动画**
 借助 Android 的过渡框架，您只需提供`起始布局和结束布局`，即可为界面中的各种运动添加动画效果。也就是说我们只需要提供两个场景，代表动画前后，然后就可以自动生成动画了。要注意的是，两个场景其实在一个页面中。



```kotlin
//两个场景的布局
    <FrameLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/scene_root">

        <include layout="@layout/a_scene" />
    </FrameLayout>
    
//场景一
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/scene_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="26sp"
        android:id="@+id/text_view1"
        android:text="Text Line 1" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="26sp"
        android:id="@+id/text_view2"
        android:text="Text Line 2" />
</LinearLayout>

//场景二
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/scene_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/text_view2"
        android:textSize="22sp"
        android:text="Text Line 2" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="22sp"
        android:id="@+id/text_view1"
        android:text="Text Line 1" />
</LinearLayout>

//获取场景，开始场景间的动画，从场景一变化为场景二

        val sceneRoot: ViewGroup = findViewById(R.id.scene_root)
        val aScene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.a_scene, this)
        val anotherScene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.another_scene, this)

        titletv.setOnClickListener {
            TransitionManager.go(anotherScene)
        }
```

5）**在 `Activity` 之间添加动画**
 刚才是同一页面不同场景之间的动画，如果是不同页面呢？也就是不同的Activity之间的动画呢？更简单了哈哈，可以在`style`中设置具体的动画，也可以直接设置过渡动画，还可以设置`共享控件`完成过渡动画。



```kotlin
//样式中定义动画
      <item name="android:windowEnterTransition">@transition/explode</item>
      <item name="android:windowExitTransition">@transition/explode</item>
    

//设置过渡动画，可以在两个布局中设置共享控件，android:transitionName="robot"
        val intent = Intent(this, Activity2::class.java)
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        val options = ActivityOptions
                .makeSceneTransitionAnimation(this, androidRobotView, "robot")
        // start the new activity
        startActivity(intent, options.toBundle())
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fanimation)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fanimation)

#### 表情符号

> EmojiCompat 支持库旨在让 Android 设备及时兼容最新的表情符号。它可防止您的应用以 ☐ 的形式显示缺少的表情符号字符，该符号表示您的设备没有用于显示文字的相应字体。通过使用 EmojiCompat 支持库，您的应用用户无需等到 Android OS 更新即可获取最新的表情符号。

这一模块就是为了兼容性提供的一个库：`EmojiCompat`，通过CharSequence文本中的 emoji 对应的`unicode 编码`来识别 emoji 表情，将他们替换成EmojiSpans，最后呈现 emoji 表情符号。

![img](https:////upload-images.jianshu.io/upload_images/8690467-fa89e1d599063d64.png?imageMogr2/auto-orient/strip|imageView2/2/w/600/format/webp)

emoji.png



```kotlin
//导入库
implementation "com.android.support:support-emoji:28.0.0"

//初始化
EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
EmojiCompat.init(config);
       
//替换组件
<android.support.text.emoji.widget.EmojiTextView
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>  
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fui%2Flook-and-feel%2Femoji-compat)

#### Fragment

> Fragment 表示 FragmentActivity 中的行为或界面的一部分。您可以在一个 Activity 中组合多个片段，从而构建多窗格界面，并在多个 Activity 中重复使用某个片段。您可以将片段视为 Activity 的模块化组成部分，它具有自己的生命周期，能接收自己的输入事件，并且您可以在 Activity 运行时添加或移除片段（这有点像可以在不同 Activity 中重复使用的“子 Activity”）。
>  片段必须始终托管在 Activity 中，其生命周期直接受宿主 Activity 生命周期的影响。

我确实没想到`fragment`也被归入到jetpack了，哈哈，这里我就贴一篇[我觉得写得好的文章](https://www.jianshu.com/p/d9143a92ad94)，虽然文章比较老了，但是可以帮你更深理解`Fragment`。
 当然官方也发布了Fragment的管理框架——`Navigation`，感兴趣的在本文搜索下即可。

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Fcomponents%2Ffragments)

#### 布局

> 布局可定义应用中的界面结构（例如 Activity 的界面结构）。布局中的所有元素均使用 View 和 ViewGroup 对象的层次结构进行构建。View 通常绘制用户可查看并进行交互的内容。然而，ViewGroup 是不可见容器，用于定义 View 和其他 ViewGroup 对象的布局结构

布局部分主要注意下比较新的两个布局`ConstraintLayout`和`MotionLayout`。

- `ConstraintLayout`现在用的已经很多了，确实很好用，特别是复杂的大型布局，与RelativeLayout属关系布局，但是更加灵活，也可以配合Android Studio的布局编辑器使用，具体用法还是比较多的，贴上[官网链接](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fconstraint-layout)。
- `MotionLayout` 是一种布局类型，可帮助您管理应用中的运动和微件动画。MotionLayout是 `ConstraintLayout` 的子类，在其丰富的布局功能基础之上构建而成。

所以`MotionLayout`就是带动画的ConstraintLayout呗，这里举个🌰看看效果：



```kotlin
   implementation 'androidx.constraintlayout:constraintlayout:2.0.0-beta8'

<androidx.constraintlayout.motion.widget.MotionLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/motionLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layoutDescription="@xml/scene_01"
    tools:showPaths="true">

    <View
        android:id="@+id/button"
        android:layout_width="64dp"
        android:layout_height="64dp"
        android:background="@color/colorAccent"
        android:text="Button" />

</androidx.constraintlayout.motion.widget.MotionLayout>


//scene_01.xml
<?xml version="1.0" encoding="utf-8"?>
<MotionScene xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:motion="http://schemas.android.com/apk/res-auto">

    <Transition
        motion:constraintSetStart="@+id/start"
        motion:constraintSetEnd="@+id/end"
        motion:duration="1000">
        <OnSwipe
            motion:touchAnchorId="@+id/button"
            motion:touchAnchorSide="right"
            motion:dragDirection="dragRight" />
    </Transition>

    <ConstraintSet android:id="@+id/start">
        <Constraint
            android:id="@+id/button"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_marginStart="8dp"
            motion:layout_constraintBottom_toBottomOf="parent"
            motion:layout_constraintStart_toStartOf="parent"
            motion:layout_constraintTop_toTopOf="parent" >

            <CustomAttribute
                motion:attributeName="backgroundColor"
                motion:customColorValue="#D81B60" />
        </Constraint>
    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@+id/button"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_marginEnd="8dp"
            motion:layout_constraintBottom_toBottomOf="parent"
            motion:layout_constraintEnd_toEndOf="parent"
            motion:layout_constraintTop_toTopOf="parent" >

            <CustomAttribute
                motion:attributeName="backgroundColor"
                motion:customColorValue="#9999FF" />
        </Constraint>
    </ConstraintSet>

</MotionScene>
```

运行效果如下：



![img](https:////upload-images.jianshu.io/upload_images/8690467-0001a71f6bc281b6.gif?imageMogr2/auto-orient/strip|imageView2/2/w/718/format/webp)

motionlayout.gif

主要是通过`app:layoutDescription="@xml/scene_01"`设定动画场景，然后在scene_01场景中就可以设置起始和结束位置，动画属性，就可以完成对动画的设置了。是不是有点`自定义view`那味了，关键这个只需要布局一个xml文件就可以了！还不试试？

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Fguide%2Ftopics%2Fui%2Fdeclaring-layout)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fmotionlayout)

#### 调色板

> 出色的视觉设计是应用成功的关键所在，而配色方案是设计的主要组成部分。调色板库是一个支持库，用于从图片中提取突出颜色，帮助您创建具有视觉吸引力的应用。

没想到吧，Android还有官方的调色板库—`Palette`。那到底这个调色板能做什么呢？主要用来分析图片中的`色彩特性`。比如图片中的暗色，亮色，鲜艳颜色，柔和色，文字颜色，主色调，等等。



```kotlin
   implementation 'com.android.support:palette-v7:28.0.0'

    //同步分析图片并获取实例
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

   //异步分析图片并获取实例
    fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            // Use generated instance
        val mutedColor = palette!!.getMutedColor(Color.BLUE)
        //主色调
        val rgb: Int? = palette?.vibrantSwatch?.rgb
        //文字颜色
        val bodyTextColor: Int? = palette?.vibrantSwatch?.bodyTextColor
        //标题的颜色
        val titleTextColor: Int? = palette?.vibrantSwatch?.titleTextColor 
        }
    }
    
```

[官方文档](https://links.jianshu.com/go?to=https%3A%2F%2Fdeveloper.android.google.cn%2Ftraining%2Fmaterial%2Fpalette-colors)      
 [Demo代码地址](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo%2Ftree%2Fmaster%2Fapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fpanda%2Fjetpackdemo%2Fpalette)

### 总结

终于告一段落了，大家吃🌰应该吃饱了吧哈哈。
 希望这篇文章能让不怎么熟悉`Jetpack`的同学多了解了解。
 当然，这还远远不够，在我看来，本文更像是一个`科普文`，只是告诉了大家jetpack大家庭有哪些成员，有什么用处。实际项目中，我们还需要建立`MVVM`的思想，深刻了解每个组件的设计意义，灵活运用组件。如果大家感兴趣，后面我会完整做一个MVVM的项目，并通过文章的形式记录整个过程。（附件也有一个项目是官方的`Jetpack实践项目`）
 最后希望大家都能通过`jetpack`构建高质量，简易并优质的项目架构，从而解放生产力，成为`效率达人`。

### 附件：

[Jetpack实践官方Demo—Sunflower](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fandroid%2Fsunflower)      
 [文章相关所有Demo](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FJiMuzz%2Fjimu-Jetpack-Demo)



作者：积木zz
链接：https://www.jianshu.com/p/f22e1f64c505
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。