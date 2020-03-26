package kr.ac.jejunu.myrealtrip.ui.newsviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.util.SingleLiveEvent
import java.util.*

class NewsViewModel(
    private val repository: Repository
) : BaseViewModel() {
    companion object{
        private val TAG = "NewsViewModel"
    }
    val newsItemsLiveData : LiveData<List<Optional<NewsItem>>> = repository.getNewsItems().toFlowable(BackpressureStrategy.BUFFER).toLiveData()
    init {
        loadNewsItems()
    }

    fun reload() {
        loadNewsItems()
    }

    private fun loadNewsItems() {
        repository.loadRss().subscribe({},{
            Log.d(TAG,"error : ${it.message}")
        }).let {
            addDisposable(it)
        }
    }
}