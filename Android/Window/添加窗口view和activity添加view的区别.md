引用《深入理解Android内核设计思想》书中的一句话

**整个界面就像由N个演员参与的话剧：SurfaceFling是摄像机，它只负责客观的捕捉当前的画面，然后真实的呈现给观众；WMS就是导演，它要负责话剧的舞台效果、演员站位；ViewRoot就是各个演员的长相和表情，取决于它们各自的条件与努力。可见，WMS与SurfaceFling的一个重要区别就是——后者只做与“显示”相关的事情，而WMS要处理对输入事件的派发**。

下面简单讲解下添加窗口view和activity添加view的区别

## 添加窗口view
```java
WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
wm.addView(mProView, layoutParams);
```
获取wm的方式其实和activity添加view流程是一样的，都是先通过getSystemService方式去获取WindowManager服务，只不过activity添加view的流程多了一步就是如下代码
```java
mWindowManager = ((WindowManagerImpl)wm).createLocalWindowManager(this);

public WindowManagerImpl createLocalWindowManager(Window parentWindow) {
        return new WindowManagerImpl(mContext, parentWindow);
    }

public WindowManagerImpl createPresentationWindowManager(Context displayContext) {
        return new WindowManagerImpl(displayContext, mParentWindow);
    }
```
会创建一个WindowManagerImpl对象，并把mParentWindow对象传入（**而添加窗口的流程并没有该代码行，默认会构造一个WindowManagerImpl对象但是少了一个mParentWindow对象以null代替传入**）


添加窗口的流程通过getSystemService获取到WindowManager对象后，因为WindowManagerImpl是WindowManager实现类，从而由WindowManagerImpl中的addview方法添加，从而调到WindowManagerGlobal.addview中，最终添加至window

## activity添加view

首先在AndroidThread中执行performLaunchActivity方法，这个方法里面创建Activity，并且会执行Activity的==attach方法来创建**PhoneWindow(也就是说每次新建activity就会创建一个window)**==，创建完PhoneWindow之后会执行Window的**setWindowManager**方法，**去创建WindowManagerImpl对象**

「**注意**：通过添加窗口流程我们得知activity添加view流程获取windowmanager对象都是通过getSystemService来获取，但是**唯一区别**是activity添加view流程**会构造一个WindowManagerImpl对象并把mParentWindow对象传入**，具体逻辑在**setWindowManager**方法中

而**mParentWindow**对象的作用就是调用其**adjustLayoutParamsForSubWindow**方法将Window中mAppToken赋值给传进来的参数wp,之后WindowManagerGlobal调用ViewRootImpl的setView将参数wparams中的**token**传给WMS

**token的作用**：每创建一个activity都会有对应的token，**AMS、WMS中的token就是为了校验是来自哪个activity，从而作为维护Activity-window一致性的判断**」
```java
public void setWindowManager(WindowManager wm, IBinder appToken, String appName,
            boolean hardwareAccelerated) {
        mAppToken = appToken;
        mAppName = appName;
        mHardwareAccelerated = hardwareAccelerated
                || SystemProperties.getBoolean(PROPERTY_HARDWARE_UI, false);
        if (wm == null) {
            wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        mWindowManager = ((WindowManagerImpl)wm).createLocalWindowManager(this);
}
```


在WindowManagerImpl里面会**和WindowManagerGlobal进行关联**，从而通过**addView方法**，removeView等方法来做为管理View的桥梁，传递给**WindowManagerGlobal**。

**PhoneWindow它是Window的唯一实现类**，我们的activity执行onCreate时，会走到setContentView，这个方法继续走下去就会调用到**PhoneWindow中的setContentView**，在这个方法里会创建**decorview**。

到这里，我们还需要把view添加到window中，有两个地方会调用：
* Activity的makeVisible方法会调用
* ActivityThread的**handleResumeActivity**会直接执行addView方法


**ActivityThread#handleResumeActivity**方法，代码如下：
```java
final void handleResumeActivity(IBinder token, boolean clearHide, boolean isForward) { 
    //...
    ActivityClientRecord r = performResumeActivity(token, clearHide); // 这里会调用到onResume()方法

    if (r != null) {
        final Activity a = r.activity;

        //...
        if (r.window == null && !a.mFinished && willBeVisible) {
            r.window = r.activity.getWindow(); // 获得window对象
            View decor = r.window.getDecorView(); // 获得DecorView对象
            decor.setVisibility(View.INVISIBLE);
            ViewManager wm = a.getWindowManager(); // 获得windowManager对象
            WindowManager.LayoutParams l = r.window.getAttributes();
            a.mDecor = decor;
            l.type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION;
            l.softInputMode |= forwardBit;
            if (a.mVisibleFromClient) {
                a.mWindowAdded = true;
                wm.addView(decor, l); // 调用addView方法
            }
            //...
        }
    }
}
```

WindowManagerImpl为WindowManager的实现类。WindowManagerImpl内部方法实现都是由**代理类WindowManagerGlobal完成**

而WindowManagerGlobal是一个单例，也就是**一个进程中只有一个WindowManagerGlobal对象服务于所有页面的View**。

从上面的代码我们可以知道如下信息
```java
 ViewManager wm = a.getWindowManager(); // 获得windowManager对象
```
1、**wm**对象就是由**WindowManagerImpl**实例化而来的，**WindowManagerImpl是WindowManager实现类**，而WindowManager又继承于**ViewManager**

2、通过其调用了**wm.addView(decor, l)**，又因为**WindowManagerImpl**内部方法实现都是**由代理类WindowManagerGlobal完成的**，所以最终走到了**WindowManagerGlobal**的**addView**方法，并在里面创建**ViewRootImpl**


```java
public void addView(View view, ViewGroup.LayoutParams params,
                    Display display, Window parentWindow){
    ...
    synchronized (mLock) {
        ...
        //创建ViewRootImp
        root = new ViewRootImpl(view.getContext(), display);
        ...
    }
    try {
         //关联WindowManager和DocorView
        root.setView(view, wparams, panelParentView);
    } catch (RuntimeException e) {
        ...
        throw e;
    }
}
```
如上创建完ViewRootImpl后，通过调用其setView方法将DecorView、WindowManager传入进而关联。

```java
public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView) {
    synchronized (this) {
         ...
        mView = view;
            ...
        requestLayout();
            ...
        try {
                ...
            res = mWindowSession.addToDisplay(mWindow, mSeq, mWindowAttributes,
                    getHostVisibility(), mDisplay.getDisplayId(),
                    mAttachInfo.mContentInsets, mInputChannel);
        } catch (RemoteException e) {
               ...
        }
    }
}
```
setView()方法还会先调用requestLayout()，完成布局的第一次layout过程，然后调用==addToDisplay(),来添加Window==（**mWindowSession.addToDisplay调用的是Session的addToDisplay()方法,此处涉及一次与WMS的IPC通信**）最终完成界面添加，不难看出**ViewRootImpl充当着界面更新及window添加的角色**，而在**WindowManagerGlobal的addView方法**中，**首先将Window所对应的View、ViewRootImpl、LayoutParams顺序添加在WindowManager中，由其进行统一管理**。