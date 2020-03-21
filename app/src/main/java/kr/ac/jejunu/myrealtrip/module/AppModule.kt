package kr.ac.jejunu.myrealtrip.module

import com.google.gson.GsonBuilder
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModelImpl
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModelImpl
import kr.ac.jejunu.myrealtrip.model.service.HtmlService
import kr.ac.jejunu.myrealtrip.model.service.RssService
import kr.ac.jejunu.myrealtrip.ui.newsfragment.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel.NewsItemViewModel
import kr.ac.jejunu.myrealtrip.util.JsoupConverterFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

private val gson = GsonBuilder()
    .setLenient()
    .create()
var retrofit = module {
    single<RssService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(RssService::class.java)
    }
    single<HtmlService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(JsoupConverterFactory)
            .build()
            .create(HtmlService::class.java)
    }
}

var model = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
    factory<HtmlDataModel> {
        HtmlDataModelImpl(
            get()
        )
    }
}

var viewModel = module {
    viewModel {
        NewsViewModel(get())
    }
    viewModel {
        NewsItemViewModel(get())
    }
}

var adapter = module {
    factory {
        NewsAdapter(get())
    }
}

var modules = listOf(retrofit, model, viewModel, adapter)