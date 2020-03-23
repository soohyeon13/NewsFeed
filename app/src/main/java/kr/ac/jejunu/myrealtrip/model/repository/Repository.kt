package kr.ac.jejunu.myrealtrip.model.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.model.data.NewsItem
import kr.ac.jejunu.myrealtrip.model.data.Rss
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import kr.ac.jejunu.myrealtrip.model.service.HtmlService
import kr.ac.jejunu.myrealtrip.model.service.RssService
import org.jsoup.nodes.Document

interface Repository {
    fun loadRss() : Completable
    fun getRssSubject() : Observable<Rss>
    fun getHtmlSubject() : Observable<Map<String?, NewsItem>>
}