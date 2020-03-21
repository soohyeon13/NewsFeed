package kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel

import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.model.service.HtmlService
import org.jsoup.nodes.Document

class HtmlDataModelImpl(private val service : HtmlService) :
    HtmlDataModel {
    override fun getHtml(url:String,query : String): Single<Document> {
        return service.getHtml(url,query)
    }
}