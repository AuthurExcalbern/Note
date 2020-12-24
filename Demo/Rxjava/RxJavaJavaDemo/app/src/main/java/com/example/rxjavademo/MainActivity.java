package com.example.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import model.Base;
import model.Card;
import model.City;
import model.Mall;
import util.Factory;
import util.FactoryCity;
import util.FactoryMall;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private final static String[] CITY = {"深圳市", "广州市", "珠海市"};
    private final static String[] MALL = {"麦当劳", "星巴克", "真功夫", "肯德基"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.HiButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Base> cityList = createBaseList(CITY, new FactoryCity());

                final List<Base> mallList = createBaseList(MALL, new FactoryMall());

                Observable<Base> city = createObservable(cityList);

                Observable<Base> mall = createObservable(mallList);

                Observer<String> card = createObserver();

                mSubscribe(city, mall, card);
            }
        });
    }

    private List<Base> createBaseList(String[] mData, Factory mFactory) {
        final List<Base> result = new ArrayList<>();
        for(String s : mData){
            result.add(mFactory.create(s));//这里数据类型会 向上转型为 Base
        }
        return result;
    }

    private Observable<Base> createObservable(final List<Base> baseList) {
        return Observable.create(new ObservableOnSubscribe<Base>() {
            @Override
            public void subscribe(ObservableEmitter<Base> emitter) {
                for (Base c : baseList) {
                    Log.d(TAG, mPrint(c.getName(), " 发送事件："));
                    emitter.onNext(c);
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    private Observer<String> createObserver() {
        return new Observer<String>() {//观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {//响应事件
                Log.d(TAG, mPrint(s, " 响应事件："));
            }

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

    private void mSubscribe(Observable<Base> cityObservable, Observable<Base> mallObservable, Observer<String> mObserver) {

        //使用zip操作符将事件1：1合并
        Observable.zip(cityObservable, mallObservable, new BiFunction<Base, Base, Card>() {
            @Override
            public Card apply(Base city, Base mall) throws Exception {
                return new Card(city.getName(), mall.getName());
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
        }).subscribe(mObserver);

    }

    private String mPrint(String msg, String type) {
        return "线程id：" + Thread.currentThread().getId() + type + msg;
    }
}
