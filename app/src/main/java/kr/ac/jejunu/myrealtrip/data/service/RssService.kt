package kr.ac.jejunu.myrealtrip.data.service

import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.data.response.RssResponse
import retrofit2.http.GET

interface RssService {
    @GET("rss?hl=ko&gl=KR&ceid=KR:ko")
    fun searchRss() : Single<RssResponse>
}