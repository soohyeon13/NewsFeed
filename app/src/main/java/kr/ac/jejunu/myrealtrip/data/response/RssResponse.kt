package kr.ac.jejunu.myrealtrip.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss",strict = false)
data class RssResponse(
    @field:Element(name = "channel")
    var channelResponse : ChannelResponse? = null
)

@Root(name = "channel",strict = false)
data class ChannelResponse(
    @field:ElementList(name = "item",inline = true,required = false)
    var itemResponse:List<ItemResponse>? = null
)

@Root(name = "item",strict = false)
data class ItemResponse(
    @field:Element(name = "title")
    var title :String? = null,
    @field:Element(name = "link")
    var link : String? = null
)