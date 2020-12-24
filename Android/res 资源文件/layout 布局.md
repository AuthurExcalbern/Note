参考：[布局](https://www.jianshu.com/p/68e086bf43d8)

## LinearLayout      线性布局

- `android:orientation`决定子控件的排布方向的属性
- `android:gravity`表示这个容器里所有**子类控件**的统一**排布**方式，有以下几个常用个选值
  + center水平和垂直方向均居中
  + center_vertical垂直居中
  + center_horizontal水平居中
  + right最右
  + left最左
  + bottom最下
  + 可用符号|实现多级连用，如`android:gravity=“bottom|right"`
- `android:layout_gravity`表示**这个子控件相对于父容器的位置**。
  + 可选值和gravity相同，也可多级联用。
  + 当然这些选值使得子控件是在它父容器里可获得的空间里进行的，如果这个控件周围还有别的控件可能会影响子控件的位置
- `android:layout_weight`局部属性，是指子类控件占当前父容器的比例

## RelativeLayout  相对布局


相对于父容器，相关的属性有：
- `android:layout_alignParentBottom`在父容器最下，true或false
- `android:layout_alignParentTop`在父容器最上
- `android:layout_alignParentLeft`在父容器最左
- `android:layout_alignParentRight`在父容器最右
- `android:layout_marginTop`和父容器上端的距离，单位dp
- `android:layout_marginBottom`和父容器下端的距离
- `android:layout_marginLeft`和父容器左端的距离
- `android:layout_marginRight`和父容器右端的距离
- `android:layout_margin`和父容器四周的距离
- `android:layout_centerVertical`在父类的垂直居中，true或false
- `android:layout_centerHorizontal`在父类的水平居中
- `android:layout_centerInParent`在父类的水平垂直居中

一种是相对于其他控件，相关的属性有：
- `android:layout_below`位于某控件下方，以id标记
- `android:layout_above`位于某控件上方
- `android:layout_toLeftOf`位于某控件左方
- `android:layout_toRightOf`位于某控件右方
- `android:layout_alignBottom`与某控件底部对齐，以id标记
- `android:layout_alignTop`与某控件顶部对齐
- `android:layout_alignLeft`与某控件左边缘对齐
- `android:layout_alignRight`与某控件右边缘对齐
- `android:layout_alignBaseline`与某控件的文本内容在一条直线上

注意相对布局里没有layout_weight属性，上面展示的第一种相对于父容器的属性就就足够。

## FrameLayout     帧布局

- `android:foreground`设置改帧布局容器的前景图像，前景图像是永远处于帧布局最上面的图像，就是不会被覆盖的图片
- `android:foregroundGravity`设置前景图像显示的位置

## AbsoluteLayout绝对布局

绝对布局是子控件通过它x，y位置来决定其位置。即`android:layout_x`和`android:layout_y`属性

## TableLayout      表格布局

## GridLayout        网格布局

## ConstraintLayout    约束布局

## 几点布局原则

- 尽量多使用线性布局和相对布局，不用绝对布局。
- 在布局层次一样下，线性布局比相对布局的性能要高。
- 使用include标签增加UI的复用效率：可把重复使用的控件抽取出来放在一个xml文件里，并在需要它的xml文件里通过include标签引用。这样做也保证了UI布局的规整和易维护性。
- 使用ViewStub标签减少布局的嵌套层次，它和include一样可以用来引入一个外部布局，但不同的是，ViewStub引入的布局不占用位置，在解析layout布局是节省了CPU和内存。可用inflate方法使之在布局中显示出来。
- 使用merge标签降低UI布局的嵌套层次：适用于布局根节点是FrameLayout且不设置background和padding等额外属性；当某个布局作为子布局被其他布局include的时候可用merge当作该布局的顶节点。