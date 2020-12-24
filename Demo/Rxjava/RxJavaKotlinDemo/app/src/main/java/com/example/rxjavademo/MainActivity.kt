package com.example.rxjavademo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import model.Card
import model.City
import model.Mall
import java.util.*

import util.RxUtil

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() { //协程结构化并发

    companion object {
        private const val TAG = "MainActivity"
        private val CITY = arrayOf("深圳市", "广州市", "珠海市")
        private val MALL = arrayOf("麦当劳", "星巴克", "真功夫", "肯德基")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.HiButton)

        btn.setOnClickListener {
            launch {
                /* 当用户手抖狂点 button 时会发生什么？防止抖动？
                 * Observable.create<T> 在多个线程/协程中被调用可能会导致什么多线程问题？
                 */

                val cityAsync = async { initCity() }
                val mallAsync = async { initMall() }

                /* 经试验
                 *   await() 调用会阻塞等待 async 执行完毕获得返回值后，再继续执行
                 *   所以不能连接调用 async{}.await()，因为这会使 async 毫无作用
                 */
                val mallObservable = mallAsync.await()
                val cityObservable = cityAsync.await()

                val card = RxUtil().createObserver()

                //把 subscribe 提取出来使代码可读性更高
                mObservable(cityObservable, mallObservable).subscribe(card)
            }
        }
    }

    private suspend fun initCity(): Observable<City> {
        //这是高级函数，区分 sam 接口转化，1.3 及之前版本也可以这样写
        val cityList = createCityList { City(it) }
        return RxUtil().createObservable(cityList)//泛型
    }

    private suspend fun initMall(): Observable<Mall> {
        val mallList = createMallList { Mall(it) }
        return RxUtil().createObservable(mallList)
    }

    private suspend fun createCityList(createCity: (s: String) -> City): List<City> {
        val result: MutableList<City> = ArrayList()
        for (s in CITY) {
            RxUtil().mPrint("创建 $s", "Create City")
            delay(1000)
            result.add(createCity(s))
        }
        return result
    }

    private suspend fun createMallList(createMall: (s: String) -> Mall): List<Mall> {
        val result: MutableList<Mall> = ArrayList()
        for (s in MALL) {
            RxUtil().mPrint("创建 $s", "Create Mall")
            delay(1000)
            result.add(createMall(s))
        }
        return result
    }

    private fun createObserver(): Observer<String> {
        return object : Observer<String> {
            //观察者
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: String) { //响应事件
                mPrint(s, " 响应事件")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
    }

    private fun <T> createObservable(baseList: List<T>): Observable<T> {
        return Observable.create<T> { //emitter ->
            for (c in baseList) {
                mPrint(c.toString(), " 发送事件")
                it.onNext(c)
            }
        }.subscribeOn(Schedulers.newThread())
    }


    private fun <C, M> mObservable(cityObservable: Observable<C>, mallObservable: Observable<M>): Observable<String> {
        return Observable.zip(cityObservable, mallObservable, BiFunction<C, M, Card> { city, mall ->
            Card(city.toString(), mall.toString()) }
        ).observeOn(AndroidSchedulers.mainThread() //观察者在主线程输出
        ).filter { card ->
            card.cityName == "深圳市"
        }.map { card ->
            card.toString()
        }
    }

    //表达式返回，字符串模板
    private fun mPrint(msg: String, type: String): Int =
            Log.d(TAG,"线程id：${Thread.currentThread().id} $type: $msg")

    override fun onDestroy() {
        super.onDestroy()
        cancel()//作用域取消
    }
}