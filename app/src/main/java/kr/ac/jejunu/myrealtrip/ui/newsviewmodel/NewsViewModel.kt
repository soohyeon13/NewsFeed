package kr.ac.jejunu.myrealtrip.ui.newsviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import kr.ac.jejunu.myrealtrip.model.data.Rss
import kr.ac.jejunu.myrealtrip.model.data.State

class NewsViewModel(private val model: DataModel) : BaseViewModel() {
    private val TAG = "NewsViewModel"
    private var _rssLiveData = MutableLiveData<Rss>()
    val rssLiveData: LiveData<Rss>
        get() = _rssLiveData

//    private var _isLoading = MutableLiveData<Boolean>()
//    val isLoading : LiveData<Boolean>
//        get() =_isLoading
//    private val rssDataSourceFactory : RssDataSourceFactory
//    private val pageSize = 4
//    init {
//        rssDataSourceFactory = RssDataSourceFactory(model,compositeDisposable)
//        val config = PagedList.Config.Builder()
//            .setPageSize(10)
//            .setInitialLoadSizeHint(38)
//            .setEnablePlaceholders(false)
//            .build()
//        rssLiveData = LivePagedListBuilder<Int,Item>(rssDataSourceFactory,config).build()
//    }
//
//    fun retry() {
//        rssDataSourceFactory.rssDataSourceLiveData.value?.retry()
//    }
//    fun listIsEmpty():Boolean {
//        if (rssLiveData.value?.isEmpty()!!) {
//            _isLoading.postValue(false)
//        }else {
//            _isLoading.postValue(true)
//        }
//        return rssLiveData.value?.isEmpty() ?:true
//    }
//
//    fun getState():LiveData<State> =
//        Transformations
//            .switchMap(rssDataSourceFactory.rssDataSourceLiveData,RssDataSource::state)

    fun getNews() {
        addDisposable(
            model.getRss()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if (channel!!.item!!.isNotEmpty()) {
                            _rssLiveData.postValue(this)
                        }
                    }
                }, {
                    Log.d(TAG, "error : ${it.message}")
                })
        )
    }
}