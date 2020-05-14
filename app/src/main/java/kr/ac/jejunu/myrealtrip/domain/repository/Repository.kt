package kr.ac.jejunu.myrealtrip.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import kr.ac.jejunu.myrealtrip.data.response.Items
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

interface Repository {
    fun loadNews() : Completable
    fun getNewsItems(page:Int) : Observable<List<NewsItem>>
    fun clear()
}