package kr.ac.jejunu.myrealtrip.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import java.util.*
import kotlin.collections.ArrayList

interface Repository {
    fun loadRss() : Completable
    fun getRss() : Observable<RssResponse>
    fun getHtml() : Observable<Map<String?, NewsItem>>
    fun getNews(title:String) : Observable<NewsItem>
    fun getNewsItems() : Observable<List<Optional<NewsItem>>>
}