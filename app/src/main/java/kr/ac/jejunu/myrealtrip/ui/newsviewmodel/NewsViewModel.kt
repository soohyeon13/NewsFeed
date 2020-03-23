package kr.ac.jejunu.myrealtrip.ui.newsviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.model.data.Rss
import kr.ac.jejunu.myrealtrip.model.repository.Repository

class NewsViewModel(
    private val repository: Repository
) : BaseViewModel() {
    private val TAG = "NewsViewModel"
    private var _rssLiveData = MutableLiveData<Rss>()
    val rssLiveData: LiveData<Rss>
        get() = _rssLiveData

    init {
        getNews()
    }

    private fun getNews() {
        addDisposable(repository.getRssSubject().subscribe({
            Log.d("$TAG get", "$it")
            _rssLiveData.value = it
        }, {
            Log.d(TAG, "${it.message}")
        }))
    }
//    private fun getNews() {
//        addDisposable(
//            model.getRss()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    it.run {
//                        if (channel!!.item!!.isNotEmpty()) {
//                            _rssLiveData.value = this
//                        }
//                    }
//                }, {
//                    Log.d(TAG, "error : ${it.message}")
//                })
//        )
//    }
}