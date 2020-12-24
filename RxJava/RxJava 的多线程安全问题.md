# RxJava 的多线程安全问题

对于RxJava，大家应该都很熟悉，他最核心的两个字就是异步，诚然，它对异步的处理非常的出色，但是**异步绝对不等于并发，更不等于线程安全**，如果把这几个概念搞混了，错误的使用RxJava，是会来带非常多的问题的。

## 什么操作符不是线程安全的？

基本上，所有操作一个Observable的 [操作符](http://reactivex.io/documentation/operators.html)：take(n), map(), distinctUntilChanged()等等。

除了带scheduler的操作符，比如：window(…, scheduler), debounce(…, scheduler),等等。

> 别在这里找一个完整的列表，只需尝试去理解什么样的操作符是线程安全的，什么不是。

## 什么操作符是线程安全的？

通常来说，所有操作多个Observable的操作符都是线程安全的：merge(), combineLatest(), zip()等等。

它们对下游数据流做序列化，让非线程安全的下游数据流操作符能正常工作。

> 还是那句话，别在这里要完整的列表。理解原理！

## 那么Subject呢?

这就是问题的所在...所有的Subject都不是线程安全的，除了SerializedSubject。

是的，你喜欢的PublishSubject 和 BehaviorSubject都不是线程安全的。

这其实有点危险！因为subject通常在不同的代码块中被共享，它们可能并行运行在不同的线程中。

> 这点我是吸取过教训的，我们有一个subject以及来自网络请求的多个数据流，它最终打断了下游数据流distinctUntilChanged() 和我们的业务逻辑。

### 去, 既然同步的Subject是危险的，那么我该如何做呢？

序列化之! 模式:

```kotlin
fun threadSafeSubject(subject: Subject) = if (you.writeToItFromMultipleThreads()) {  
  subject.toSerialized() // Serialize it! Now!
} else {
  subject // You're fine, use it as is.
}
```

如果你把Subject作为一个 event bus使用这尤其危险。比如[我们在 StorIO中所做的那样](https://github.com/pushtorefresh/storio/blob/22db81c48a4ecc73e1dc042e65dfb6b815b94909/storio-common/src/main/java/com/pushtorefresh/storio/internal/RxChangesBus.java#L15)。因为数据库中的改变发生在不同的线程，有并发的可能，我们把subject序列化，这样就保证了用户的安全。

## 我该对我自定义的同步发射数据的Observable做些什么？

首先，不要使用Observable.create()：见 [RxJava#PR#4253](https://github.com/ReactiveX/RxJava/pull/4253)。

其次，你需要序列化Observable发射的数据，最简单的方式就是调用[serialize()](http://reactivex.io/documentation/operators/serialize.html) 。

## What should I do in general with concurrency and RxJava?

只要不违背 [The Observable Contract](http://reactivex.io/documentation/contract.html)，并且如果你并行发射数据请serialize() Observable。

## 实例：多个Observables从多个线程中发射数据给同一个Observer

```java
final PublishSubject<Integer> subject = PublishSubject.create();

subject.subscribe(new Subscriber<Integer>() {
    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onNext(Integer integer) {
        unSafeCount = unSafeCount + integer;
        Log.d("TAG", "onNext: " + unSafeCount);
    }
});

findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final int unit = 1;
        for(int i = 0;i < 10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        subject.onNext(unit);
                    }
                }
            }).start();
        }
    }
});
```

这是一个最典型的多线程问题，从10个线程中发射数据并相加，这样最终得到的答案是小于10000的。虽然使用了RxJava，但是这样的使用对于并发是没有意义的，因为**RxJava并没有去处理并发带来的问题**。我们可以看下subject的onNext方法的源码，里面很简单，就是调用了对应observer的onNext方法而已。不止是这样，绝大多数的Subject都是线程不安全的，所以当你在使用这样的类的时候(典型场景就是自制的RxBus)，如果从多个线程中发射数据，那你就要小心了。

对于这样的问题，有两种解决方案：

- 第一种就是简单的使用传统的解决方法，比如用AtomicInteger代替int。
- 第二种则是使用RxJava的解决方案，在这里就是用SerializedSubject去代替Subject：

```java
final PublishSubject<Integer> subject = PublishSubject.create();

subject.subscribe(new Subscriber<Integer>() {
    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {}

    @Override
    public void onNext(Integer integer) {
        unSafeCount = unSafeCount + integer;
        count.addAndGet(integer);

        Log.d("TAG", "onNext: " + count);
    }
});

final SerializedSubject<Integer, Integer> ser = new SerializedSubject<Integer, Integer>(subject);

findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final int unit = 1;

        for(int i = 0;i < 10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0;j < 1000;j++){
                        ser.onNext(unit);
                    }
                }
            }).start();
        }
    }
});
```

SerializedSubject的onNext方法处理方式很简单，如果有其他线程在发射数据，那就将数据放置到队列中，等待下次发射。这保证了同一时间只会有一个线程调用onNext，onComplete和onError这些方法。

但是这样操作显然是会造成性能的影响的，所以RxJava并不会把所有的操作都打上线程安全的标签。

在这里就要引申出一个问题，那就是使用者对create方法的滥用，其实这个方法不应该被使用者频繁的调用的，因为你必须要小心的处理所有的数据发射，接收的逻辑。相反的，使用已有的操作符能很好的解决这个问题，所以下次大家在遇到问题的时候不要简单的使用create，去自己写，而是应该想想有没有现成的操作符可以完成相应的需求。

## 实例：merge和concat

对于多线程发射数据，有时候我们需要得到的结果也保持和发射时候一样的顺序，这个时候如果我们使用merge这个操作符去结合多个发射源，那么就会产生一定的问题了(例子中做了非常不好的示范——使用了create操作符，请大家不要学习这样的写法，这里单纯是为了求证结果)。

```java
Observable o1 = Observable.create(new Observable.OnSubscribe<Integer>() {
    @Override
    public void call(final Subscriber<? super Integer> subscriber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
});
Observable o2 = Observable.create(new Observable.OnSubscribe<Integer>() {
    @Override
    public void call(Subscriber<? super Integer> subscriber) {
        subscriber.onNext(2);
        subscriber.onCompleted();
    }
});

Observable.merge(o1,o2)
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer i) {
                Log.d("TAG", "onNext: " + i);
            }
        });
```

对于这样的场景，我们得到的答案将是2,1而不是先得到o1发射的数据，再获取o2的数据。

究其原因，就是因为merge其实就是给什么传什么，也不会去管数据发射的顺序

一个单词说明这个问题：interleaving——交错。merge后的数据源可能是交错的。由于merge有这样数据交错的问题，所以它的变种—flatMap也会有同样的问题。

根据文档，我们知道concat操作符是一个接一个的处理数据源的数据的。通过源码我们可以知道，active字段就保证了如果上一个数据源还没有发射完数据，就会一直在for循环中等待，直到上一个数据源发射完了数据重置了active字段。

对于concat，其实还存在一个问题，那就是多个Observable变成了串行，会大大的增加整个RxJava事件流的处理时间，对于这个场景，我们可以使用concatEager来解决。concatEager的源码就不带大家分析了，有兴趣的同学可以自行查看。