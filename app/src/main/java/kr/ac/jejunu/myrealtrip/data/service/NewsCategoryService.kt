package kr.ac.jejunu.myrealtrip.data.service

import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.data.response.NewsCateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCategoryService {
    @GET("top-headlines")
    fun categoryNews(
        @Query("country") country : String,
        @Query("category") category: String,
        @Query("apiKey") apiKey : String
    ): Single<NewsCateResponse>
}