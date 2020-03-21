package kr.ac.jejunu.myrealtrip.model.service

import io.reactivex.Single
import org.jsoup.nodes.Document
import retrofit2.http.*

interface HtmlService {
    @GET("/__i/rss/rd/articles/{url}")
    fun getHtml(@Path("url")url:String,@Query("query") query:String) : Single<Document>
}