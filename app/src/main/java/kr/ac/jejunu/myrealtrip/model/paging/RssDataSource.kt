package kr.ac.jejunu.myrealtrip.model.paging
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.PageKeyedDataSource
//import io.reactivex.Completable
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.functions.Action
//import io.reactivex.internal.operators.completable.CompletableFromAction
//import io.reactivex.schedulers.Schedulers
//import kr.ac.jejunu.myrealtrip.model.data.Channel
//import kr.ac.jejunu.myrealtrip.model.data.Item
//import kr.ac.jejunu.myrealtrip.model.data.Rss
//import kr.ac.jejunu.myrealtrip.model.data.State
//import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
//
//class RssDataSource(
//    private val model: DataModel,
//    private val compositeDisposable: CompositeDisposable
//) : PageKeyedDataSource<Int, Item>() {
//    private var retryCompletable: Completable? = null
//    var state : MutableLiveData<State> = MutableLiveData()
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, Item>
//    ) {
//        compositeDisposable.add(
//            model.getRss(1, params.requestedLoadSize)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ response ->
//                    updateState(State.DONE)
//                    response.channel?.item?.let { callback.onResult(it, null, 2) }
//                }, {
//                    updateState(State.ERROR)
//                    setRetry(Action { loadInitial(params, callback) })
//                })
//        )
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
//        compositeDisposable.add(
//            model.getRss(params.key, params.requestedLoadSize)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ response ->
//                    updateState(State.DONE)
//                    response.channel?.item?.let { callback.onResult(it, params.key + 1) }
//                }, {
//                    updateState(State.ERROR)
//                    setRetry(Action { loadAfter(params, callback) })
//                })
//        )
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
//    }
//
//    fun retry() {
//        if (retryCompletable != null) {
//            compositeDisposable.add(
//                retryCompletable!!
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
//            )
//        }
//    }
//    private fun updateState(state: State) {
//        this.state.postValue(state)
//    }
//    private fun setRetry(action: Action?) {
//        retryCompletable = if (action == null) null else Completable.fromAction(action)
//    }
//}