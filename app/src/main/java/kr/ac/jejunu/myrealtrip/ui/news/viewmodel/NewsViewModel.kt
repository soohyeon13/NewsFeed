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
    private var page = 1
    val newsItemsLiveData  = repository.getNewsItems(page).toLiveData()

    fun reload() {
        loadNewsItems()
    }

    fun getPage(page:Int) {
        this.page = page
    }

    private fun loadNewsItems() {
        repository.loadRss().subscribe({},{
            Log.d(TAG,"error : ${it.message}")
        }).let {
            addDisposable(it)
        }
    }
}