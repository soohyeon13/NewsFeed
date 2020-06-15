package kr.ac.jejunu.myrealtrip.ui.cate.tab.recent_news

import android.util.Log
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.util.toLiveData

class RecentNewsViewModel(private val repository: Repository) :
    BaseViewModel() {
    companion object{
        private const val TAG = "RecentNewsViewModel"
    }
    private var page = BehaviorSubject.createDefault(1)
    val newsItemsLiveData = page.concatMapEager { page -> repository.getNewsItems(page) }.toLiveData()

    fun reload() {
        loadNewsItems()
    }

    fun clear() {
        repository.clear()
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
}