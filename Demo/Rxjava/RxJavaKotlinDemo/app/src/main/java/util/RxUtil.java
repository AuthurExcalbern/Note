package util;

import android.util.Log;

import com.example.rxjavademo.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.internal.Intrinsics;
import model.Card;

public class RxUtil {
    private static final String TAG  = "RxUtil";

    public final <T> Observable<T> createObservable(final List<T> baseList) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                for (T c : baseList) {
                    mPrint(c.toString(), " 发送事件：");
                    emitter.onNext(c);
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    public Observer<String> createObserver() {
        return new Observer<String>() {//观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) { mPrint(s, " 响应事件："); }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }

    public final <C, M> Observable<String> mSubscribe(Observable<C> cityObservable, Observable<M> mallObservable, Observer<String> mObserver) {

        //使用zip操作符将事件1：1合并
        return Observable.zip(cityObservable, mallObservable, new BiFunction<C, M, Card>() {
            @Override
            public Card apply(C city, M mall) {
                return new Card(city.toString(), mall.toString());
            }
        }).observeOn(AndroidSchedulers.mainThread()//观察者在主线程输出
        ).filter(new Predicate<Card>() {//过滤 深圳市
            @Override
            public boolean test(Card card) {
                return card.getCityName().equals("深圳市");
            }
        }).map(new Function<Card, String>() {//把卡片转换为字符串
            @Override
            public String apply(Card card) {
                return card.toString();
            }
        });

    }

    public final void mPrint(String msg, String type) {
        Log.d(TAG,"线程id：" + Thread.currentThread().getId() + type +": " + msg);
    }
}
