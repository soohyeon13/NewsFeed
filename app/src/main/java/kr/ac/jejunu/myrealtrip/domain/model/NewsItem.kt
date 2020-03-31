package kr.ac.jejunu.myrealtrip.domain.model

data class NewsItem(
    var title: String? = "",
    var imageUrl : String? = "",
    var desc : String? = "",
    var content : String? = "",
    var link : String? ="",
    var keyWord : List<String>? = null
)