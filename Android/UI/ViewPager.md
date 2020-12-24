## 一、简介

------

Viewpager，视图翻页工具，提供了多页面切换的效果。Android 3.0后引入的一个UI控件，位于v4包中。低版本使用需要导入v4包，但是现在我们开发的APP一般不再兼容3.0及以下的系统版本，另外现在大多数使用Android studio进行开发，默认导入v7包，v7包含了v4，所以不用导包，越来越方便了。

Viewpager使用起来就是我们通过创建adapter给它填充多个view，左右滑动时，切换不同的view。Google官方是建议我们使用Fragment来填充ViewPager的，这样 可以更加方便的生成每个Page，以及管理每个Page的生命周期。

Viewpager在Android开发中使用频率还是比较高的，下面开始一起学习吧！

## 二、基本使用

------

### 1. xml引用

```xml
<android.support.v4.view.ViewPager
    android:id="@+id/vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</android.support.v4.view.ViewPager>
```

### 2. page布局

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView
    android:id="@+id/tv"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FAE8DA"
    android:gravity="center"
    android:text="Hello"
    android:textSize="22sp">
</TextView>
```

### 3. 创建适配器

可直接new PagerAdapter，亦可创建它的子类

```java
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mData;

    public MyPagerAdapter(Context context ,List<String> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_base,null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(mData.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
```

### 4. 设置适配器

```java
private void setVp() {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
       list.add("第"+i+"个View");
    }

    ViewPager vp = (ViewPager) findViewById(R.id.vp);
    vp.setAdapter(new MyPagerAdapter(this,list));
}
```

效果：
![img](https://img-blog.csdn.net/20180228134517956?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 5. 标题栏

给Viewpager设置标题栏有一下几种方式：

- PagerTabStrip： 带有下划线
- PagerTitleStrip： 不带下划线
- TabLayout：5.0后推出

TabLayout的详细使用，可以看我的另一篇文章[TabLayout](http://blog.csdn.net/weixin_39251617/article/details/79032641)。

下面介绍另外两个的使用方法，没什么区别：

#### 1. xml引用

```xml
<android.support.v4.view.ViewPager
    android:id="@+id/vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v4.view.PagerTitleStrip
        android:id="@+id/pager_title"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:background="@android:color/white"
        android:layout_gravity="top"
        android:textColor="#ff0000"
        android:textSize="18sp">

    </android.support.v4.view.PagerTitleStrip>

</android.support.v4.view.ViewPager>


 <android.support.v4.view.ViewPager
    android:id="@+id/vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v4.view.PagerTabStrip
        android:id="@+id/pager_tab"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_gravity="top"
        android:background="@android:color/white"
        android:textColor="#ff0000">
    </android.support.v4.view.PagerTabStrip>
    
</android.support.v4.view.ViewPager>

```

#### 2. 重写PagerAdapter的getTitle()方法

```java
 @Override
public CharSequence getPageTitle(int position) {
    return mTitles[position];
}
```

这两种方法作为了解，不常用，项目中还没用到过

效果：
![img](https://img-blog.csdn.net/20180228134544515?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 6. 翻页动画

ViewPager有个方法叫做：

setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) 用于设置ViewPager切换时的动画效果，并且google官方还给出了两个示例（因为使用的是属性动画，所以不兼容3.0以下）。

#### 1. DepthPageTransformer

```java
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);
            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
```

调用：

```java
vp.setPageTransformer(false,new DepthPageTransformer());
```

效果：
![img](https://img-blog.csdn.net/20180228134621852?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

#### 2. ZoomOutPageTransformer

```java
public class ZoomOutPageTransformer implements ViewPager.PageTransformer
{
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @SuppressLint("NewApi")
    public void transformPage(View view, float position)
    {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        Log.e("TAG", view + " , " + position + "");
        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0)
            {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else
            {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
```

调用：

```java
vp.setPageTransformer(false,new ZoomOutPageTransformer());
```

效果：
![img](https://img-blog.csdn.net/20180228134717990?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

#### 3. 自定义动画

网上看到鸿洋大神写的

```java
public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private static final float ROT_MAX = 20.0f;
    private float mRot;


    public void transformPage(View view, float position)
    {
        Log.e("TAG", view + " , " + position + "");
        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setRotation(0);
        } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0)
            {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation( mRot);
            } else
            {
                mRot = (ROT_MAX * position);
                view.setPivotX(view.getMeasuredWidth() * 0.5f);
                view.setPivotY(view.getMeasuredHeight());
                view.setRotation( mRot);
            }
            // Scale the page down (between MIN_SCALE and 1)
            // Fade the page relative to its size.
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setRotation( 0);
        }
    }
}
```

效果：
![img](https://img-blog.csdn.net/20180228134731704?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

**position说明：**
当前显示页为0，前一页为-1，后一页为1，滑动过程中数值不断变大或变小，所以为float类型

#### 4. 开源框架ViewPagerTransforms

里面有十几种翻页动画，基本够用了
Github地址：[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)

### 7. 翻页监听

#### 1. 设置方法

```java
addOnPageChangeListener()
```

#### 2. 翻页监听接口

```java
ViewPager.OnPageChangeListener
```

#### 3. 重写方法

1. **onPageScrolled**(int position, float positionOffset, int positionOffsetPixels)

   页面滑动状态停止前一直调用

   position：当前点击滑动页面的位置
   positionOffset：当前页面偏移的百分比
   positionOffsetPixels：当前页面偏移的像素位置

2. **onPageSelected**(int position)

   滑动后显示的页面和滑动前不同，调用

   position：选中显示页面的位置

3. **onPageScrollStateChanged**(int state)

   页面状态改变时调用

   state：当前页面的状态

   SCROLL_STATE_IDLE：空闲状态
   SCROLL_STATE_DRAGGING：滑动状态
   SCROLL_STATE_SETTLING：滑动后滑翔的状态

#### 4. 使用

```java
vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("vp","滑动中=====position:"+ position + "   positionOffset:"+ positionOffset + "   positionOffsetPixels:"+positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("vp","显示页改变=====postion:"+ position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                Log.e("vp","状态改变=====SCROLL_STATE_IDLE====静止状态");
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                Log.e("vp","状态改变=====SCROLL_STATE_DRAGGING==滑动状态");
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                Log.e("vp","状态改变=====SCROLL_STATE_SETTLING==滑翔状态");
                break;
        }
    }
});
```

Log：
![img](https://img-blog.csdn.net/20180228134809744?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

## 三、与Fragment结合使用

------

与Fragment结合使用其实也一样，只是用Fragment代替原先的View，填充Viewpager；然后就是Adapter不一样，配合Fragment使用的有两个Adapter：**FragmentPagerAdapter**和**FragmentStatePagerAdapter**。

**相同点：**
FragmentPagerAdapter和FragmentStatePagerAdapter都继承自PagerAdapter

**不同点：**
卸载不再需fragment时，各自采用的处理方法有所不同

FragmentStatePagerAdapter会销毁不需要的fragment。事务提交后， activity的FragmentManager中的fragment会被彻底移除。 FragmentStatePagerAdapter类名中的“state”表明：在销毁fragment时，可在onSaveInstanceState(Bundle)方法中保存fragment的Bundle信息。用户切换回来时，保存的实例状态可用来恢复生成新的fragment

FragmentPagerAdapter有不同的做法。对于不再需要的fragment， FragmentPagerAdapter会选择调用事务的detach(Fragment)方法来处理它，而非remove(Fragment)方法。也就是说， FragmentPagerAdapter只是销毁了fragment的视图， fragment实例还保留在FragmentManager中。因此，FragmentPagerAdapter创建的fragment永远不会被销毁

也就是：在destroyItem()方法中，FragmentStatePagerAdapter调用的是remove()方法，适用于页面较多的情况；FragmentPagerAdapter调用的是detach()方法，适用于页面较少的情况。但是有页面数据需要刷新的情况，不管是页面少还是多，还是要用FragmentStatePagerAdapter，否则页面会因为没有重建得不到刷新

使用如下：

### 1. 创建Fragment及相应的xml布局

```xml
public class PagerFragment extends Fragment {
    String mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContent = (String) getArguments().get("content");
        View view = inflater.inflate(R.layout.fragment_pager, container, false) ;
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(mContent);
        return view;
    }

}

<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.strivestay.viewpagerdemo.PagerFragment">

    <TextView
        android:id="@+id/tv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:textSize="18sp"
        android:text="@string/hello_blank_fragment"/>

</FrameLayout>

```

### 2. 给Viewpager设置数据和适配器

```java
private void setVp() {
    final List<PagerFragment> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content","第"+i+"个Fragment");
        fragment.setArguments(bundle);

        list.add(fragment);
    }

    ViewPager vp = (ViewPager) findViewById(R.id.vp);
//        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//        });

    vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    });

}
```

效果：
![img](https://img-blog.csdn.net/20180228134826949?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2VpeGluXzM5MjUxNjE3/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)