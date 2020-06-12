package kr.ac.jejunu.myrealtrip.data.response

data class NewsCateResponse(
    val status : String,
    val totalResults : Int,
    val articles : List<NewsCateItems>
)

data class NewsCateItems(
    val source : Source,
    val author : String,
    val title : String,
    val description : String,
    val url : String,
    val urlToImage : String,
    val publishedAt : String,
    val content : String

)

data class Source(
    val id : Int,
    val name : String
)