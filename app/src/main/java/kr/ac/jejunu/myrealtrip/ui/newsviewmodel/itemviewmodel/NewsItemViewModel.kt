package kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.myrealtrip.base.BaseItemViewModel
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
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

    override fun bind(data: Optional<NewsItem>) {
        if (data.isPresent) {
            data.get().let {
                _imageUrl.value = it.imageUrl
                _des.value = it.desc
                _title.value = it.title
                if (it.keyWord?.isNotEmpty()!!) {
                    _firstKeyWord.value = it.keyWord?.get(0)
                    _secondKeyWord.value = it.keyWord?.get(1)
                    _thirdKeyWord.value = it.keyWord?.get(2)
                }
//
//                it.keyWord.let {word ->
//                    _firstKeyWord.value = word?.get(0)
//                    _secondKeyWord.value = word?.get(1)
//                    _thirdKeyWord.value = word?.get(2)
//                }
            }
        }
    }
}

























