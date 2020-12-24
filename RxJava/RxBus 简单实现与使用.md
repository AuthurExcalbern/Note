# RxBus 简单实现与使用

## 引入库：

```bash
    implementation 'io.reactivex:rxjava:1.1.0'
    implementation 'io.reactivex:rxandroid:1.1.0'
```

## 创建Rxbus操作类：

Subject是非线程安全的，在并发情况下，不推荐使用通常的Subject对象，而是推荐使用SerializedSubject。

```java
public class RxBus {
    private static volatile RxBus instance;
    private Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    /**
     * 发送事件
     * @param object
     */
    public void post(Object object) {
        bus.onNext(object);
    }

    /**
     * 根据类型接收相应类型事件
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
```

## 在BaseActivity中保存和取消订阅事件

```java
public class BaseActivity extends AppCompatActivity {
    protected ArrayList<Subscription> rxBusList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearSubscription();
    }

    /**
     * 取消该页面所有订阅
     */
    private void clearSubscription() {
        for (Subscription subscription : rxBusList) {
            if (subscription != null && subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
```

## 发送的对象实体类

```cpp
public class EventBean {
    private int userId;
    private String nickName;

    public EventBean(int userId, String nickName) {
        this.userId = userId;
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
```

## 发送事件方式

```cpp
RxBus.getDefault().post(new EventBean(1, "听说名字长回头率很高"));
```

## 接收事件方式

```java
        Subscription subscription = RxBus.getDefault().toObservable(EventBean.class)
            .subscribe(new Action1<EventBean>() {
            @Override
            public void call(EventBean eventBean) {
                tvContent.setText(eventBean.getUserId() + "------" + eventBean.getNickName());
            }
        });
        rxBusList.add(subscription);
```