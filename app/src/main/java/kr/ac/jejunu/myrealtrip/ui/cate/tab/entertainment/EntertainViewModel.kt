package kr.ac.jejunu.myrealtrip.ui.cate.tab.entertainment

import android.util.Log
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.util.Cate
import kr.ac.jejunu.myrealtrip.util.toLiveData

class EntertainViewModel(private val cateRepository: CategoryRepository) :
    BaseViewModel() {
    companion object{
        private const val TAG = "EntertainViewModel"
        private val cate = Cate.ENTERTAINMENT.query
    }
    val entertainNewsItemsLiveData = cateRepository.getCateNews(Cate.ENTERTAINMENT.query).toLiveData()

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