package kr.ac.jejunu.myrealtrip.model.service

import io.reactivex.Observable
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.model.data.Channel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.Rss
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RssService {
    @GET("/rss?hl=ko&gl=KR&ceid=KR:ko")
    fun searchRss() : Single<Rss>
}