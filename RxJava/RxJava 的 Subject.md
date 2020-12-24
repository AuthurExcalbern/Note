# RxJava 的 Subject

Subject有两种用途：

1. 做为observable向其他的observable发送事件
2. 做为observer接收其他的observable发送的事件。
    最后的一个例子会使用一个subject监听一个observable，并将observable发射的事件转发给一个observer。

## Subject做为Observable发送事件

| Subject         | 发射行为                                     |
| --------------- | -------------------------------------------- |
| AsyncSubject    | 不论订阅发生在什么时候，只会发射最后一个数据 |
| BehaviorSubject | 发送订阅之前一个数据和订阅之后的全部数据     |
| ReplaySubject   | 不论订阅发生在什么时候，都发射全部数据       |
| PublishSubject  | 发送订阅之后全部数据                         |

### AsyncSubject

Observer会接收AsyncSubject的onComplete()之前的最后一个数据。

```java
AsyncSubject<String> subject = AsyncSubject.create();
        subject.onNext("asyncSubject1");
        subject.onNext("asyncSubject2");
        subject.onComplete();
        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("asyncSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("asyncSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("asyncSubject:complete");  //输出 asyncSubject onComplete
            }
        });

        subject.onNext("asyncSubject3");
        subject.onNext("asyncSubject4");
```

执行结果：

```java
asyncSubject:asyncSubject2
asyncSubject:complete
```

改一下代码，将subject.onComplete()放在最后。

```java
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.onNext("asyncSubject1");
        subject.onNext("asyncSubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("asyncSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("asyncSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("asyncSubject:complete");  //输出 asyncSubject onComplete
            }
        });

        subject.onNext("asyncSubject3");
        subject.onNext("asyncSubject4");
        subject.onComplete();
```

执行结果：

```java
asyncSubject:asyncSubject4
asyncSubject:complete
```

注意，subject.onComplete()必须要调用才会开始发送数据，否则Subscriber将不接收任何数据。

### BehaviorSubject

Observer会接收到BehaviorSubject被订阅之前的最后一个数据，再接收订阅之后发射过来的数据。如果BehaviorSubject被订阅之前没有发送任何数据，则会发送一个默认数据。

```java
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("behaviorSubject1");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("behaviorSubject:"+s); 
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("behaviorSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("behaviorSubject:complete");  //输出 behaviorSubject onComplete
            }
        });

        subject.onNext("behaviorSubject2");
        subject.onNext("behaviorSubject3");
```

执行结果：

```java
behaviorSubject:behaviorSubject1
behaviorSubject:behaviorSubject2
behaviorSubject:behaviorSubject3
```

在这里，behaviorSubject1是默认值。因为执行了

```java
BehaviorSubject<String> subject = BehaviorSubject.createDefault("behaviorSubject1");
```

稍微改一下代码，在subscribe()之前，再发射一个事件。

```java
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("behaviorSubject1");
        subject.onNext("behaviorSubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("behaviorSubject:"+s);  //输出asyncSubject:asyncSubject3
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("behaviorSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("behaviorSubject:complete");  //输出 behaviorSubject onComplete
            }
        });

        subject.onNext("behaviorSubject3");
        subject.onNext("behaviorSubject4");
```

执行结果：

```java
behaviorSubject:behaviorSubject2
behaviorSubject:behaviorSubject3
behaviorSubject:behaviorSubject4
```

这次丢弃了默认值，而发射behaviorSubject2。

因为BehaviorSubject 每次只会发射调用subscribe()方法之前的最后一个事件和调用subscribe()方法之后的事件。

BehaviorSubject还可以缓存最近一次发出信息的数据。

### ReplaySubject

ReplaySubject会发射所有来自原始Observable的数据给观察者，无论它们是何时订阅的。

```java
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.onNext("replaySubject1");
        subject.onNext("replaySubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("replaySubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("replaySubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("replaySubject:complete");  //输出 replaySubject onComplete
            }
        });

        subject.onNext("replaySubject3");
        subject.onNext("replaySubject4");
```

执行结果：

```java
replaySubject:replaySubject1
replaySubject:replaySubject2
replaySubject:replaySubject3
replaySubject:replaySubject4
```

稍微改一下代码，将create()改成createWithSize(1)只缓存订阅前最后发送的1条数据

```java
        ReplaySubject<String> subject = ReplaySubject.createWithSize(1);
        subject.onNext("replaySubject1");
        subject.onNext("replaySubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("replaySubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("replaySubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("replaySubject:complete");  //输出 replaySubject onComplete
            }
        });

        subject.onNext("replaySubject3");
        subject.onNext("replaySubject4");
```

执行结果：

```java
replaySubject:replaySubject2
replaySubject:replaySubject3
replaySubject:replaySubject4
```

这个执行结果跟BehaviorSubject是一样的。但是从并发的角度来看，ReplaySubject 在处理并发 subscribe() 和 onNext() 时会更加复杂。

ReplaySubject除了可以限制缓存数据的数量和还能限制缓存的时间。使用createWithTime()即可。

### PublishSubject

Observer只接收PublishSubject被订阅之后发送的数据。

```java
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("publicSubject1");
        subject.onNext("publicSubject2");
        subject.onComplete();

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("publicSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("publicSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("publicSubject:complete");  //输出 publicSubject onComplete
            }
        });

        subject.onNext("publicSubject3");
        subject.onNext("publicSubject4");
```

执行结果：

```java
publicSubject:complete
```

因为subject在订阅之前，已经执行了onComplete()方法，所以无法发射数据。稍微改一下代码，将onComplete()方法放在最后。

```java
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("publicSubject1");
        subject.onNext("publicSubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("publicSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("publicSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("publicSubject:complete");  //输出 publicSubject onComplete
            }
        });

        subject.onNext("publicSubject3");
        subject.onNext("publicSubject4");
        subject.onComplete();
```

执行结果：

```java
publicSubject:publicSubject3
publicSubject:publicSubject4
publicSubject:complete
```

### 可能错过的事件

Subject 作为一个Observable时，可以不停地调用onNext()来发送事件，直到遇到onComplete()才会结束。

```java
PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                },new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("completed");
                    }
                });
        subject.onNext("Foo");
        subject.onNext("Bar");
        subject.onComplete();
```

执行的结果：

```java
Foo
Bar
completed
```

如果，使用 subsribeOn 操作符将 subject 切换到IO线程，再使用 Thread.sleep(2000) 让主线程休眠2秒。

```java
 PublishSubject<String> subject = PublishSubject.create();
        subject.subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                },new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("completed");
                    }
                });
        subject.onNext("Foo");
        subject.onNext("Bar");
        subject.onComplete();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

这时，其执行的结果变为：

```java
completed
```

为何会缺少打印Foo和Bar？

因为，subject 发射元素的线程被指派到了 IO 线程，此时 IO 线程正在初始化还没起来，subject 发射前这两个元素Foo、Bar还在主线程中，主线程的这两个元素往 IO 线程转发的过程中由于 IO 线程还没有起来，所以就被丢弃了。此时，无论Thread睡了多少秒，Foo、Bar都不会被打印出来。

其实，解决办法也很简单，将subject改成使用Observable.create()来替代，它允许为每个订阅者精确控制事件的发送，这样就不会缺少打印Foo和Bar。

## Subject做为observable

```java
  private void doObserverSubject() {
        //将Subject当作Observer使用，并将另外的observer注册到subject上，来监听原始的observable发出的事件
        List<String> items = new ArrayList<>();
        items.add("100");
        items.add("103");
        items.add("107");
        Observable<String> observable = Observable.from(items);
        ReplaySubject<String> replay = ReplaySubject.create();
        observable.subscribe(replay);
        replay.subscribe(new SubjectObserver<String>("first"));
    }
```

