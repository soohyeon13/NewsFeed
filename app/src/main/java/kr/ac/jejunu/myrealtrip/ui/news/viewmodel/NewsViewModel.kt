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

class NewsViewModel() : BaseViewModel() {
    companion object {
        private val TAG = "NewsViewModel"
    }
}