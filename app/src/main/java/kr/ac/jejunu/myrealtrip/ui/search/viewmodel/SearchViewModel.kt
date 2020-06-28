package kr.ac.jejunu.myrealtrip.ui.search.viewmodel

import android.util.Log
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.activity.NewsMainViewModel
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.SearchRepository
import kr.ac.jejunu.myrealtrip.util.toLiveData

class SearchViewModel(val searchRepository: SearchRepository) :BaseViewModel() {
    companion object{
        private val TAG = "SearchViewModel"
    }
    private var page = BehaviorSubject.createDefault(1)
    val searchItemsLiveData = searchRepository.getSearchNews().toLiveData()
    fun loadSearchResult(word : String) {
        searchRepository.loadSearchNews(word,page.value!!).subscribe({},{
            Log.d(TAG,it.message)
        }).let { addDisposable(it) }
    }
    fun plusPage() {
        page.onNext(page.value!!+1)
    }
}