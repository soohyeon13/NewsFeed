package kr.ac.jejunu.myrealtrip.ui.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.domain.repository.SearchRepository
import kr.ac.jejunu.myrealtrip.util.toLiveData

class NewsViewModel(
    private val repository: Repository,
    private val searchRepository: SearchRepository,
    private val cateRepository : CategoryRepository
) : BaseViewModel() {
    companion object {
        private val TAG = "NewsViewModel"
    }

    private var page = BehaviorSubject.createDefault(1)
    private var searchPage = BehaviorSubject.createDefault(1)
    val newsItemsLiveData = page.concatMapEager { page -> repository.getNewsItems(page) }.toLiveData()
    val searchItemLiveData = searchRepository.getSearchNews().toLiveData()
    val cateNewsItemsLiveData = cateRepository.getCateNews().toLiveData()
    fun reload() {
        loadNewsItems()
    }

    fun clear(type: String) {
        if (type == "search") searchRepository.clear()
        else repository.clear()
    }

    fun search(news: String) {
        searchRepository.loadSearchNews(news, searchPage.value!!).subscribe({}, {
            Log.d(TAG, "search error ${it.message}")
        }).let {
            addDisposable(it)
        }
    }

    fun loadPage() {
        page.onNext(page.value!! + 1)
    }

    private fun loadNewsItems() {
        repository.loadNews().subscribe({ page.onNext(1) }, {
            Log.d(TAG, "error : ${it.message}")
        }).let {
            addDisposable(it)
        }
    }

    fun loadCateNews(query : String) {
        cateRepository.clear()
        cateRepository.loadCateNews("kr",query,BuildConfig.Google_News_Api_key).subscribe({},{
            Log.d(TAG,it.message)
        }).let { addDisposable(it) }
    }
}