package kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.ac.jejunu.myrealtrip.base.BaseItemViewModel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel

class NewsItemViewModel(private val htmlModel: HtmlDataModel) : BaseItemViewModel<Item>() {
    private val META_IMAGE_TAG = "meta[property=og:image]"
    private val META_DESCRIPTION_TAG = "meta[property=og:description]"
    private val META_CONTENT = "content"
    private val URL_QUERY = "oc=5"
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

    override fun bind(data: Item) {
        _title.postValue(data.title)
        getHtml(data.link.toString())
    }

    private fun getHtml(url: String) {
        val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
        val resultRegex = regex!!.value
        addDisposable(
            htmlModel.getHtml(resultRegex, URL_QUERY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ it ->
                    it.run {
                        this.head().select(META_IMAGE_TAG).forEach {
                            val image = it.attr(META_CONTENT)
                            _imageUrl.postValue(image)
                        }
                        this.head().select(META_DESCRIPTION_TAG).forEach {
                            val d = it.attr(META_CONTENT)
                            _des.postValue(d)
                        }
                    }
                }, {
                    Log.d(TAG, "error : ${it.message}")
                })
        )
    }
}