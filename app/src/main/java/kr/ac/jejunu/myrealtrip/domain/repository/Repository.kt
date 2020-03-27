package kr.ac.jejunu.myrealtrip.domain.repository

import com.google.common.base.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

interface Repository {
    fun loadRss() : Completable
    fun getRss() : Observable<RssResponse>
    fun getHtml() : Observable<Map<String?, NewsItem>>
    fun getNews(title:String) : Observable<NewsItem>
    fun getNewsItems() : Observable<List<Single<NewsItem>>>
}