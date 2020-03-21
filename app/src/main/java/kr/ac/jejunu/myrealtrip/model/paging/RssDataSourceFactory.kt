package kr.ac.jejunu.myrealtrip.model.paging
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.DataSource
//import io.reactivex.disposables.CompositeDisposable
//import kr.ac.jejunu.myrealtrip.model.data.Item
//import kr.ac.jejunu.myrealtrip.model.data.Rss
//import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
//
//class RssDataSourceFactory(
//    private val model : DataModel,
//    private val compositeDisposable: CompositeDisposable
//) : DataSource.Factory<Int,Item>() {
//    val rssDataSourceLiveData = MutableLiveData<RssDataSource>()
//    override fun create(): DataSource<Int, Item> {
//        val rssDataSource = RssDataSource(model,compositeDisposable)
//        rssDataSourceLiveData.postValue(rssDataSource)
//        return rssDataSource
//    }
//}