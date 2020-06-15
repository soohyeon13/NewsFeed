package kr.ac.jejunu.myrealtrip.ui.cate.tab.business

import android.util.Log
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.util.Cate
import kr.ac.jejunu.myrealtrip.util.toLiveData

class BusinessViewModel(private val cateRepository : CategoryRepository) :
    BaseViewModel() {
    companion object{
        private const val TAG = "BusinessViewModel"
        private val cate = Cate.BUSINESS.query
    }
    val businessNewsItemsLiveData = cateRepository.getCateNews(Cate.BUSINESS.query).toLiveData()

    fun loadCateNews() {
        cateRepository.loadCateNews("kr",cate, BuildConfig.Google_News_Api_key).subscribe({},{
            Log.d(TAG,it.message)
        }).let { addDisposable(it) }
    }
    fun reload() {
        cateRepository.clear(cate)
        loadCateNews()
    }
}