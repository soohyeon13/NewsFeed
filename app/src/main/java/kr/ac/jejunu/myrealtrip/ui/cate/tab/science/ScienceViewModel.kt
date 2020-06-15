package kr.ac.jejunu.myrealtrip.ui.cate.tab.science

import android.util.Log
import kr.ac.jejunu.myrealtrip.BuildConfig
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.util.Cate
import kr.ac.jejunu.myrealtrip.util.toLiveData

class ScienceViewModel(private val cateRepository : CategoryRepository) :
    BaseViewModel() {
    companion object{
        private const val TAG = "ScienceViewModel"
        private val cate = Cate.SCIENCE.query
    }
    val scienceNewsItemsLiveData = cateRepository.getCateNews(Cate.SCIENCE.query).toLiveData()

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