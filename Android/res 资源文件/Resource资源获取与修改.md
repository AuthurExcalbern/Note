参考：[Android resource](https://www.jianshu.com/p/87930a20a4a8)、[Android Resource介绍和使用](https://blog.51cto.com/android/302529)

9种类型：
- animator
  - 在res/animator目录下，用来描述属性动画
- anim
  - 在res/anim目录下，用来描述补间动画
  - ``
- color
  - 在res/color目录下，用描述对象颜色状态选择子
- drawable
  - 在res/drawable目录下，用来描述可绘制对象
  - `mContext.getResources().getDrawable(R.drawable.p_w_picpathId)`
- layout
  - 在res/layout目录下，用来描述应用程序界面布局
- menu
  - 在res/menu目录下，用来描述应用程序菜单
- raw
  - 在res/raw目录下，它们和assets类资源一样，都是原装不动地打包在apk文件中的，不过它们会被赋予资源ID，这样我们就可以在程序中通过ID来访问它们
  - `mContext.getResources().openRawResource(R.raw.id)`
- values
  - 在res/values目录下，用来描述一些简单值
- xml
  - 在res/xml目录下，一般就是用来描述应用程序的配置信息
  - `mContext.getResources().getXML(R.xml.id)`

除了raw类型资源，以及Bitmap文件的drawable类型资源之外，其它的资源文件均为文本格式的XML文件，它们在打包的过程中，会被编译成二进制格式的XML文件。