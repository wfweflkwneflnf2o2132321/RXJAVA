package com.example.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val someObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        someObservable
            .doOnNext {
                Log.d("RxJava3", "doOnNext ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.newThread())
            .filter{
                it <= 7
            }
            .delay ( 5000L, TimeUnit.MILLISECONDS )
            .repeat(2)
            .map {
                it.toDouble()
            }
            .subscribe(
                {
                    Log.d("RxJava3", "onNext $it")
                },
                {},
                {}
            )

//        Single.just(1)
//            .subscribe({
//
//            }, {
//
//            })

//        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//            .onBackpressureBuffer(1000)
//            .subscribe {
//                Log.d("RxJava3", "onNext $it")
//            }



        val getFirstObserver = Observable.just(1,2,3,4,5,6,7)
        val getSecondObserver = Observable.just(1,2,3,4,5,6,7)

        val source = ReplaySubject.create<Int>()
         // Он получит 1, 2, 3, 4
        source.subscribe(getFirstObserver)
        source.onNext(1)
        Log.d("RxJava3", "onNext $getFirstObserver")
        source.onNext(2)
        Log.d("RxJava3", "onNext $getFirstObserver")
        source.onNext(3)
        Log.d("RxJava3", "onNext $getFirstObserver")
        source.onNext(4)
        Log.d("RxJava3", "onNext $getFirstObserver")
        source.onComplete()
         // Он также получит 1, 2, 3, 4 так как он использует Replay Subject
        source.subscribe(getSecondObserver)
        Log.d("RxJava3", "onNext $getSecondObserver  ")

    }
}

private fun <T> ReplaySubject<T>.subscribe(firstObserver: Observable<T>) {

}


//.subscribeOn(Schedulers.io())


//.observeOn(AndroidSchedulers.mainThread())