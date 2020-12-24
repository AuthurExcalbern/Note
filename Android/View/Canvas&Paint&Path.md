## Paint

确定绘制内容的具体效果（如颜色、大小等等）

```
// 步骤1：创建一个画笔
private Paint mPaint = new Paint();

// 步骤2：初始化画笔
// 根据需求设置画笔的各种属性，具体如下：

    private void initPaint() {

        // 设置最基本的属性
        // 设置画笔颜色
        // 可直接引入Color类，如Color.red等
        mPaint.setColor(int color); 
        // 设置画笔模式
         mPaint.setStyle(Style style); 
        // Style有3种类型：
        // 类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        // 类型2：Paint.Style.FILL（只填充不描边）
        // 类型3：Paint.Style.STROKE（只描边不填充）
        // 具体差别请看下图：
        // 特别注意：前两种就相差一条边
        // 若边细是看不出分别的；边粗就相当于加粗       
        
        //设置画笔的粗细
        mPaint.setStrokeWidth(float width)       
        // 如设置画笔宽度为10px
        mPaint.setStrokeWidth(10f);    

        // 不常设置的属性
        // 得到画笔的颜色     
        mPaint.getColor()      
        // 设置Shader
        // 即着色器，定义了图形的着色、外观
        // 可以绘制出多彩的图形
        // 具体请参考文章：http://blog.csdn.net/iispring/article/details/50500106
        Paint.setShader(Shader shader)  

        //设置画笔的a,r,p,g值
       mPaint.setARGB(int a, int r, int g, int b)      
         //设置透明度
        mPaint.setAlpha(int a)   
       //得到画笔的Alpha值
        mPaint.getAlpha()        


        // 对字体进行设置（大小、颜色）
        //设置字体大小
          mPaint.setTextSize(float textSize)       

        // 文字Style三种模式：
          mPaint.setStyle(Style style); 
        // 类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        // 类型2：Paint.Style.FILL（只填充不描边）
        // 类型3：Paint.Style.STROKE（只描边不填充） 
        
      // 设置对齐方式   
      setTextAlign（）
      // LEFT：左对齐
      // CENTER：居中对齐
      // RIGHT：右对齐

        //设置文本的下划线
          setUnderlineText(boolean underlineText)      
        
        //设置文本的删除线
        setStrikeThruText(boolean strikeThruText)    

         //设置文本粗体
        setFakeBoldText(boolean fakeBoldText)  
        
           // 设置斜体
        Paint.setTextSkewX(-0.5f);


        // 设置文字阴影
        Paint.setShadowLayer(5,5,5,Color.YELLOW);
     }

// 步骤3：在构造函数中初始化
    public CarsonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
```

## Path

设置绘制的顺序 & 区域，单使用Path无法产生效果

Path类封装了由直线和曲线（2、3次贝塞尔曲线）构成的几何路径。

[Path类的最全面详解](https://www.jianshu.com/p/2c19abde958c)

```
// 使用Path首先要new一个Path对象
// Path的起点默认为坐标为(0,0)
Path path = new Path();
// 特别注意：建全局Path对象，在onDraw()按需修改；尽量不要在onDraw()方法里new对象
// 原因：若View频繁刷新，就会频繁创建对象，拖慢刷新速度。

// 设置当前点位置
// 后面的路径会从该点开始画
moveTo(float x, float y) ；

// 当前点（上次操作结束的点）会连接该点
// 如果没有进行过操作则默认点为坐标原点。
lineTo(float x, float y)  ；

// 闭合路径，即将当前点和起点连在一起
// 注：如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么也不做
close() ；
```

## Canvas

画布:绘制时的规则，但内容实际上是绘制在屏幕上的

```
// 画一个矩形(蓝色)
canvas.drawRect(100, 100, 150, 150, mPaint1);

// 将画布的原点移动到(400,500)
canvas.translate(400,500);

// 再画一个矩形(红色)
canvas.drawRect(100, 100, 150, 150, mPaint2);
```

绘制内容是根据画布的规定绘制在屏幕上的
- 内容实际上是绘制在屏幕上；
- 画布，即Canvas，只是规定了绘制内容时的规则；
- 内容的位置由坐标决定，而坐标是相对于画布而言的

```
// 方法1
// 利用空构造方法直接创建对象
Canvas canvas = new Canvas()；

// 方法2
// 通过传入装载画布Bitmap对象创建Canvas对象
// CBitmap上存储所有绘制在Canvas的信息
Canvas canvas = new Canvas(bitmap)

// 方法3
// 通过重写View.onDraw（）创建Canvas对象
// 在该方法里可以获得这个View对应的Canvas对象

   @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //在这里获取Canvas对象
    }

// 方法4 官方推荐
// 在SurfaceView里画图时创建Canvas对象

        SurfaceView surfaceView = new SurfaceView(this);
        // 从SurfaceView的surfaceHolder里锁定获取Canvas
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        //获取Canvas
        Canvas c = surfaceHolder.lockCanvas();
        
        // ...（进行Canvas操作）
        // Canvas操作结束之后解锁并执行Canvas
        surfaceHolder.unlockCanvasAndPost(c);
```

[Canvas API 文档](https://developer.android.com/reference/android/graphics/Canvas.html)

Canvas具体使用时是在复写的onDraw（）里：
```
  @Override
    protected void onDraw(Canvas canvas){
      
        super.onDraw(canvas);
   
    // 对Canvas进行一系列设置
    //  如画圆、画直线等等
   canvas.drawColor(Color.BLUE); 
    // ...
    }
}
```