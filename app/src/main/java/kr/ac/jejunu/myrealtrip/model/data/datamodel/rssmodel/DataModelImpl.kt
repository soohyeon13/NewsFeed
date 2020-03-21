package kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel

import android.util.Log
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.model.data.Channel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.Rss
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import kr.ac.jejunu.myrealtrip.model.service.RssService

class DataModelImpl(private val service: RssService) :
    DataModel {
    override fun getRss(): Single<Rss> {
        return service.searchRss()
    }
}