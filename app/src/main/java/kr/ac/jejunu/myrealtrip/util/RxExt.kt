package kr.ac.jejunu.myrealtrip.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.toLiveData
import com.google.android.material.appbar.AppBarLayout
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

fun <T> Single<T>.toLiveData(): LiveData<T> = MutableLiveData<T>().also {
    subscribe({ t ->
        it.postValue(t)
    }, {})
}

fun <T> Observable<T>.toLiveData(): LiveData<T> =
    toFlowable(BackpressureStrategy.BUFFER).toLiveData()

fun <T> Single<T>.toObservable(): Observable<T> =
    BehaviorSubject.create<T>().also { subscribe({ t -> it.onNext(t) }, {}) }