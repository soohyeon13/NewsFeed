package kr.ac.jejunu.myrealtrip.ui.news.viewmodel

import android.util.Log
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.util.toLiveData
import java.util.*

class NewsViewModel(
    private val repository: Repository
) : BaseViewModel() {
    companion object{
        private val TAG = "NewsViewModel"
    }
    val newsItemsLiveData  = repository.getNewsItems().toLiveData()
    init {
        Log.d(TAG,Objects.toString(repository))
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