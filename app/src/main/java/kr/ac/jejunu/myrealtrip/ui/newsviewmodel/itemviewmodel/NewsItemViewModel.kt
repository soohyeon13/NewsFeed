package kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.myrealtrip.base.BaseItemViewModel
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import com.google.common.base.Optional
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import java.net.URLEncoder
import java.util.*

class NewsItemViewModel : BaseItemViewModel<NewsItem>() {
    private val TAG = "NewsItemViewModel"
    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title
    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl
    private var _des = MutableLiveData<String>()
    val des: LiveData<String>
        get() = _des
    private var _firstKeyWord = MutableLiveData<String>()
    val firstKeyWord: LiveData<String>
        get() = _firstKeyWord
    private var _secondKeyWord = MutableLiveData<String>()
    val secondKeyWord: LiveData<String>
        get() = _secondKeyWord
    private var _thirdKeyWord = MutableLiveData<String>()
    val thirdKeyWord: LiveData<String>
        get() = _thirdKeyWord

    override fun bind(data: Single<NewsItem>) {
        Log.d(TAG,data.toString())
        addDisposable(data.subscribe({
            _title.postValue(it.title)
            _des.postValue(it.desc)
           _imageUrl.postValue(it.imageUrl)
        },{}))
//        data.subscribe({
//            Log.d(TAG,"$it")
//            _imageUrl.postValue(it.imageUrl)
//            _des.postValue(it.desc)
//            _title.postValue(it.title)
//            if (!it.keyWord.isNullOrEmpty()) {
//                _firstKeyWord.postValue(it.keyWord!![0])
//                _secondKeyWord.postValue(it.keyWord!![1])
//                _thirdKeyWord.postValue(it.keyWord!![2])
//            }
//        },{
//            Log.d(TAG,"error : ${it.message}")
//        })
//        if (data.isPresent) {
//            data.get().let {
//                _imageUrl.value = it.imageUrl
//                _des.value = it.desc
//                _title.value = it.title
//                if (it.keyWord?.isNotEmpty()!!) {
//                    _firstKeyWord.value = it.keyWord?.get(0) ?: ""
//                    _secondKeyWord.value = it.keyWord?.get(1) ?: ""
//                    _thirdKeyWord.value = it.keyWord?.get(2) ?: ""
//                }
//            }
//        }
    }
}

























