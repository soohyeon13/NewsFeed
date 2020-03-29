package kr.ac.jejunu.myrealtrip.ui.news.viewmodel.itemviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.myrealtrip.base.BaseItemViewModel
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

class NewsItemViewModel() : BaseItemViewModel<NewsItem>() {
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

    override fun bind(data: NewsItem) {
        _title.value = data.title
        _imageUrl.value = data.imageUrl
        _des.value = data.desc
        if (data.keyWord?.size ==3) {
            data.keyWord?.let {
                _firstKeyWord.value = it[0]
                _secondKeyWord.value = it[1]
                _thirdKeyWord.value = it[2]
            }
        }
    }
}