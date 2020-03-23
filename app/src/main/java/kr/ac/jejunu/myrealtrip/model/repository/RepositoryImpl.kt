package kr.ac.jejunu.myrealtrip.model.repository

import android.text.Html
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.myrealtrip.model.data.NewsItem
import kr.ac.jejunu.myrealtrip.model.data.Rss
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import org.jsoup.nodes.Document
import java.util.*

class RepositoryImpl(
    private val model: DataModel,
    private val htmlModel: HtmlDataModel
) : Repository {
    companion object {
        private val META_IMAGE_TAG = "meta[property=og:image]"
        private val META_DESCRIPTION_TAG = "meta[property=og:description]"
        private val META_CONTENT = "content"
        private val BODY_ARTICLE_CONTENT = "div[itemprop=articleBody]"
        private val URL_QUERY = "oc=5"
        private val TAG = "RepositoryImpl"
    }

    private val rssSubject = BehaviorSubject.create<Rss>()
    private val newsItemsSubject = BehaviorSubject.create<Map<String?, NewsItem>>()
    override fun loadRss(): Completable {
        return Completable.fromCallable {
            model.getRss()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "start : $it")
                    it.run {
                        if (!channel?.item.isNullOrEmpty()) {
                            rssSubject.onNext(it)
                            it.channel?.item?.forEachIndexed { index, item ->
                                val url = item.link.toString()
                                val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
                                val resultRegex: String? = regex?.value
                                resultRegex?.let { reg ->
                                    htmlModel
                                        .getHtml(reg, URL_QUERY)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe({ s ->
                                            s.run {
                                                val imgUrl = this.head()
                                                    .select(META_IMAGE_TAG)
                                                    .attr(META_CONTENT)
                                                val des = this.head()
                                                    .select(META_DESCRIPTION_TAG)
                                                    .attr(META_CONTENT)
                                                val content = this.body()
                                                    .select(BODY_ARTICLE_CONTENT)
                                                    .text()
                                                newsItemsSubject
                                                    .onNext(
                                                        mapOf(
                                                            Pair(
                                                                item.title,
                                                                NewsItem(
                                                                    imageUrl = imgUrl,
                                                                    desc = des,
                                                                    content = content
                                                                )
                                                            )
                                                        )
                                                    )
                                            }
                                        }, { e ->
                                            Log.d(TAG, "error : ${e.message}")
                                        })
                                }
                            }
                        }
                    }
                }, {
                    Log.d(TAG, "error : ${it.message}")
                })
        }
//        return model.getRss()
//            .subscribeOn(Schedulers.io())
//            .doOnSuccess {
//                Log.d("$TAG run", "success")
//                it.run {
//                    if (!channel?.item.isNullOrEmpty()) {
//                        rssSubject.onNext(it)
//                        Log.d(TAG, "$it")
//                        it.channel?.item?.forEachIndexed { index, item ->
//                            val url = item.link.toString()
//                            val regex = Regex(pattern = "(?<=(/))\\w*(?=\\?)").find(url)
//                            val resultRegex = regex!!.value
//                            htmlModel.getHtml(resultRegex, URL_QUERY).subscribeOn(Schedulers.io())
//                                .subscribe({ s ->
//                                    s.run {
//                                        //흠... 이게 맞나.......
//                                        val imgUrl =
//                                            this.head().select(META_IMAGE_TAG).attr(META_CONTENT)
//                                        val des = this.head().select(META_DESCRIPTION_TAG)
//                                            .attr(META_CONTENT)
//                                        val content =
//                                            this.body().select(BODY_ARTICLE_CONTENT).text()
//                                        Log.d("$TAG response", "$imgUrl / $des / $content")
//                                        newsItemsSubject.onNext(
//                                            mapOf(
//                                                Pair(
//                                                    item.title,
//                                                    NewsItem(
//                                                        imageUrl = imgUrl,
//                                                        desc = des,
//                                                        content = content
//                                                    )
//                                                )
//                                            )
//                                        )
//                                    }
//                                }, {
//                                    Log.d(TAG, "${it.message}")
//                                })
//                        }
//                    }
//                }
//            }.ignoreElement()
    }

    override fun getRssSubject(): Observable<Rss> {
        Log.d("$TAG rss", rssSubject.value.toString())
        return rssSubject.hide()
    }

    override fun getHtmlSubject(): Observable<Map<String?, NewsItem>> {
        Log.d("$TAG html", newsItemsSubject.value.toString())
        return newsItemsSubject.hide()
    }

}