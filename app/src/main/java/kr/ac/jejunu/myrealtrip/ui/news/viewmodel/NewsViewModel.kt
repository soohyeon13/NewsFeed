package kr.ac.jejunu.myrealtrip.ui.news.viewmodel

import android.util.Log
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.util.toLiveData

class NewsViewModel(
    private val repository: Repository
) : BaseViewModel() {
    companion object {
        private val TAG = "NewsViewModel"
    }

    private var page = BehaviorSubject.createDefault(1)
    val newsItemsLiveData = page.switchMap { page -> repository.getNewsItems(page) }.toLiveData()

    fun reload() {
        loadNewsItems()
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