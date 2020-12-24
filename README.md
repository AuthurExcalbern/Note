- Android Studio
  - 效率工具
    - Android Studio 快捷键
    - Android Studio UI 工具
    - Android Studio 构建工具
    - Android Studio Profiler
    - Android Studio APK Analyzer
    - Android Studio Debug
    - Android Studio 编码工具
    - Android Studio 插件工具
  - 项目导入排错总结

---

- Git
  - Git 常用命令
  - Git Reset 三种模式
  - 版本回退
  - 提交和合并提交
  - Repo 介绍

---

- Java
  - 文档
    - 《Java 编程思想第四版（Thinking In Java）》
  - 基本
    - 常见比较
    - 面向对象思想
    - 重构技巧
    - 接口与抽象类的区别
    - 容器类
    - Java 泛型
    - 类加载机制
    - 反射机制
    - 异常处理机制
    - 注解（Annotation）
  - 线程 & 进程
    - 线程
    - 线程安全和实现
    - 锁优化
    - 线程池
  - JVM
    - 内存管理机制
    - GC&内存分配
    - 类加载机制

---

- Java 全栈知识体系
  - Java
    - Java 基础
      - 面向对象
      - 知识点
      - 图谱 & 常见问题
      - 泛型
      - 注解
      - 异常
      - 反射
    - Java 集合框架
      - Collection 类关系图
      - Collection - ArrayList 源码解析
      - Collection - LinkedList源码解析
      - Collection - Stack & Queue 源码解析
      - Collection - PriorityQueue源码解析
      - Map - HashSet & HashMap 源码解析
      - Map - LinkedHashSet&Map源码解析
      - Map - TreeSet & TreeMap 源码解析
      - Map - WeakHashMap源码解析
    - Java 多线程与并发
      - Java 并发 - 知识体系
      - Java 并发 - 理论基础
      - Java 并发 - 线程基础
      - 关键字: synchronized详解
      - 关键字: volatile详解
      - 关键字: final详解
      - JUC - 类汇总和学习指南
      - JUC原子类: CAS, Unsafe和原子类详解
      - JUC锁: LockSupport详解
      - JUC锁: 锁核心类AQS详解
      - JUC锁: ReentrantLock详解
      - JUC锁: ReentrantReadWriteLock详解
      - TODO：线程池、工具类、并发
    - Java IO & NIO & AIO
      - Java IO & NIO & AIO - Overview
      - Java IO - 分类(传输，操作)
      - Java IO - 设计模式(装饰者模式)
      - Java IO - 源码: InputStream
      - Java IO - 源码: OutputStream
      - Java IO - 常见类使用
      - IO 模型 - Unix IO 模型
      - Java IO - BIO 详解
      - Java NIO - 基础详解
      - Java NIO - IO多路复用详解
      - Java AIO - 异步IO详解
      - Java N(A)IO - 框架: Netty
      - Java NIO - 零拷贝实现
    - JVM
      - JVM 相关知识体系
      - JVM 基础 - 类字节码详解
      - JVM 基础 - Java 类加载机制
      - JVM 基础 - JVM 内存结构
      - JVM 基础 - Java 内存模型引入
      - JVM 基础 - Java 内存模型详解
      - JVM 基础 - Java 垃圾收集

---

- Kotlin
  - 文档
    - 《Kotlin 实战》
    - Kotlin 协程使用和与原理分析
    - kotlin-docs
  - 对比 Java
    - Kotlin 与 Java 的 基本对比
    - Kotlin 与 Java 的 互操作
    - Kotlin 对比 Java 的 简化语法
    - Kotlin 对比 Java 的 类继承结构
  - 扩展函数与属性
  - object 关键字：将声明一个类与创建一个实例结合起来
  - 协程
    - 结构化并发
    - Kotlin 协程基本使用
    - 协程使用详解
    - Kotlin 协程使用和与原理分析

---

- RxJava
  - RxJava 基础入门
  - 操作符
    - 创建操作符
    - 变换操作符
    - 组合/合并 操作符
    - 功能性操作符
    - 过滤操作符
    - 条件/布尔 操作符
  - 背压
  - RxJava 的 Subject
  - RxBus 简单实现与使用
  - RxJava 操作符 和 Subject 的多线程安全问题
  - 解决RxJava内存泄漏
    - RxLifecycle
    - AutoDispose

---

- 内存优化
  - C/C++ 内存基础知识
  - JVM 内存管理机制
  - GC&内存回收机制
  - 内存泄漏种类
  - 内存分析工具
    - LeakCanary
    - Android Studio Profile
    - MAT(Memory Analyzer Tool)

---

- 设计模式
  - 创建型模式：单例、建造者、工厂方法、简单工厂、抽象工厂、原型
  - 行为型模式：策略、状态、责任链、观察者、模板方法、迭代器、备忘录、访问者、中介者、解释器、命令
  - 结构型模式：代理、组合、适配器、装饰者、享元、外观、桥接

---

- Android
  - AndroidManifest配置文件
  - 四大组件
    - 四大组件概述
    - Activity
    - Service
    - BroadcastReceiver
    - ContentProvider
    - 四大组件工作过程
  - Context
  - View
    - View 的事件体系
    - Canvas&Paint&Path
    - View 的绘制原理
    - View LayoutInflater
    - 自定义View
  - 架构设计
    - 组件化
    - MVC-MVP-MVVM-Clean
    - DataBinding
    - JetPack 组件
      - ViewModel、Lifecycles、LiveData 基本使用
      - Room 基本使用
      - WorkManager 基本使用
  - UI
    - 常用控件
    - 高级控件
    - ListView&RecyclerView
    - 碎片Fragment
    - ViewPager
    - 动画Animation
  - res 资源文件
    - layout 布局
    - theme 主题
    - style 样式
    - Drawable
    - Resource资源获取与修改
  - Window
    - Android Window
    - 添加窗口view和activity添加view的区别
  - Android IPC
    - 六种 Android IPC 方式
  - 调试
    - adb 调试&Linux命令
    - adb shell 常用命令
    - adb logcat
    - adb install

---

- 单元测试
  - Android单元测试
  - JUnit
  - Mockito
  - PowerMock
  - Robolectric

---

- UML
  - 类图
  - 时序图

---

# TODO

- 插件化
  - [实战插件化-MPlugin](http://note.youdao.com/noteshare?id=caab6b569971b3bbf2eefc7a5e89cdea)
  - 插件框架
    - [RePlugin](http://note.youdao.com/noteshare?id=d0fafcdc12da7f290f65a16327f1a1a1)
- 热修复
  - [剖析ClassLoader深入热修复原理](http://note.youdao.com/noteshare?id=fbf5549a1433a8d85ff42be624add3d7)

- 操作系统
  - [基础知识](http://note.youdao.com/noteshare?id=b8e447691e30778fc1e6e899e57c72da)
- 计算机网络
  - [基础知识](http://note.youdao.com/noteshare?id=ea8abdbaf60e63444c84d3be1c87d962)
  - TCP & UDP
  - http & https
  - Socket

- JNI/NDK

## FrameWork

- NDK编程
- 架构设计
- 并行开发/多线程
- 网络管理
- Linux内核机制
- 服务
  - 应用服务
  - 系统服务
- Android 系统
  - 内存管理
  - 任务管理机制
  - 进程管理机制
  - 消息通信机制
  - 安全机制

## Android

- Dalvik虚拟机
- ART虚拟机
- Hook 机制
  - [剖析Activity启动及Hook](http://note.youdao.com/noteshare?id=cd7883b10851c1ab111fd382960b9374)
- App沙箱化
- 权限管理
- app安装流程
- app启动流程