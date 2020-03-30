package kr.ac.jejunu.myrealtrip.ui.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var _isVisible = MutableLiveData<Boolean>()
    val isVisible : LiveData<Boolean>
        get() = _isVisible
    private var _isProgress = MutableLiveData<Boolean>()
    val isProgress : LiveData<Boolean>
        get() = _isProgress
    private var page =1
    val newsItemsLiveData  = repository.getNewsItems(page).toLiveData()

    fun reload() {
        loadNewsItems()
    }

    fun loadPage(page:Int) {
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