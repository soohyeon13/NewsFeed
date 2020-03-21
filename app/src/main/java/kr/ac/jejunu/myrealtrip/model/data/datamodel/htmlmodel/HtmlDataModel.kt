package kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel

import io.reactivex.Single
import org.jsoup.nodes.Document

interface HtmlDataModel {
    fun getHtml(url:String,query:String) :Single<Document>
}