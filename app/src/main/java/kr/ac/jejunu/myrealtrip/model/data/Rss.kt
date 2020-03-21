package kr.ac.jejunu.myrealtrip.model.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "rss",strict = false)
data class Rss(
    @field:Element(name = "channel")
    var channel : Channel? = null
)

@Root(name = "channel",strict = false)
data class Channel(
    @field:ElementList(name = "item",inline = true,required = false)
    var item:List<Item>? = null
)

@Root(name = "item",strict = false)
data class Item(
    @field:Element(name = "title")
    var title :String? = null,
    @field:Element(name = "link")
    var link : String? = null
)

//data class Rss(
//    val channel:Channel
//)
//data class Channel(
//    val item:List<Item>
//)
//
//data class Item(
//    val title:String,
//    val link :String
//)
