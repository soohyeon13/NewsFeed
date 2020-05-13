package kr.ac.jejunu.myrealtrip.data.service

import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.data.response.Items
import kr.ac.jejunu.myrealtrip.data.response.RssSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {
    @GET("v1/search/news.json")
    fun getSearchNews(
        @Query("query") query: String,
        @Query("display") display: Int? =20,
        @Query("start") start: Int? = 1,
        @Query("sort") sort: String? ="sim"
    ) : Single<RssSearchResponse>
}