package kr.ac.jejunu.myrealtrip.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import java.util.*

abstract class BaseItemViewModel<T> : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
    abstract fun bind(data:Optional<T>)
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}