package kr.ac.jejunu.myrealtrip.data.response

import java.util.*

data class RssSearchResponse(
    val lastBuildDate : Date,
    val total : Int,
    val start : Int,
    val display : Int,
    val items : List<Items>
)

data class Items(
    val title:String,
    val originallink : String,
    val link : String,
    val description : String,
    val pubDate:Date
)