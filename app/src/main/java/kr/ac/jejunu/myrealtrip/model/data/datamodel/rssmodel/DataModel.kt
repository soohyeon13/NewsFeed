package kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel

import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.model.data.Channel
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.Rss

interface DataModel {
    fun getRss() : Single<Rss>
}