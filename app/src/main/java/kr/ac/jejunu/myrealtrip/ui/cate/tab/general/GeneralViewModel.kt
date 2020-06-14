package kr.ac.jejunu.myrealtrip.ui.cate.tab.general

import android.util.Log
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.util.Cate
import kr.ac.jejunu.myrealtrip.util.toLiveData

class GeneralViewModel(private val cateRepository: CategoryRepository) :
    BaseViewModel() {
    companion object{
        private const val TAG = "GeneralViewModel"
    }
    val generalNewsItemsLiveData = cateRepository.getCateNews(Cate.GENERAL.query).toLiveData()

    fun loadCateNews(query : String) {
        cateRepository.clear()
        cateRepository.loadCateNews("kr",query, BuildConfig.Google_News_Api_key).subscribe({},{
            Log.d(TAG,it.message)
        }).let { addDisposable(it) }
    }
}