package kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.myrealtrip.base.BaseItemViewModel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.repository.Repository

class NewsItemViewModel(private val repository: Repository) : BaseItemViewModel<Item>() {
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
        Log.d(TAG,repository.toString())
        data.link?.let { getHtml(it,data.title.toString()) }
        _title.value = data.title
    }

    private fun getHtml(url: String,title:String) {
        var keyWord1 = ""
        var keyWord2 = ""
        var keyWord3 = ""
        val map = mutableMapOf<String, Int?>()
//        val regex1 = Regex(pattern = "[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#\$%&\\\\\\=\\(\\'\\\"]")
        val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
        val resultRegex = regex!!.value
        addDisposable(
            repository.getHtmlSubject().subscribe({
                Log.d(TAG,"$it")
                _imageUrl.value = it[title]?.imageUrl
                _des.value = it[title]?.desc
            },{
                Log.d(TAG,"${it.message}")
            })
//            htmlModel.getHtml(resultRegex, URL_QUERY).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe({
//                    it.run {
//                        _title.value = data.title
//                        _imageUrl.value = this.head().select(META_IMAGE_TAG).attr(META_CONTENT)
//                        _des.value = this.head().select(META_DESCRIPTION_TAG).attr(META_CONTENT)
//                        val text1 = this.body().select("div[itemprop=articleBody]").text()
//                        text1.split(" ").filter { i-> i.length >2 }.forEach { s ->
//                            if (!map.containsKey(s)) map[s] = 1
//                            else map[s]?.plus(1)
//                        }
//                        Log.d("test",map.toString())
//                        val w = map.toList().sortedByDescending { (_,value) -> value }
//                        val array = mutableListOf<String>()
//                        val max = w[0].second
//                        var test = mutableListOf<String>()
//                        test.add(w[0].first)
//                        for(i in 1 until w.size) {
//                            if (w[i].second!! < max!!) {
//                                test.sort()
//                                for (i in test.indices) {
//                                    array.add(w[i-1].first)
//                                }
//                            }
//                            test.add(w[i].first)
//                        }


//                        text1.split(" ")
//                            .filter { it.length > 3 }
//                            .forEach {
//                                val str = regex1.find(it)!!.value
//                                if (!map.containsKey(str)) map[str] = 1
//                                else map[str] = map[str]?.plus(1)
//                            }
//                        Log.d("test",map.toString())
//                        val list = map.toList().sortedByDescending { (_, value) -> value }
//                        Log.d("test",list.toString())
//                        keyWord1 = list[0].first
//                        keyWord2 = list[1].first
//                        keyWord3 = list[2].first
//                        Log.d("key","keyWord1 : $keyWord1 keyWord2 : $keyWord2 keyWord3 : $keyWord3")
//                    }
//                }, {
//                    Log.d(TAG, "error : ${it.message}")
//                })
        )
    }
}