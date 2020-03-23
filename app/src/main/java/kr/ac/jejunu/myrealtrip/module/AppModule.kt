package kr.ac.jejunu.myrealtrip.module

import android.util.Log
import com.google.gson.GsonBuilder
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.rssmodel.DataModelImpl
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModelImpl
import kr.ac.jejunu.myrealtrip.model.repository.Repository
import kr.ac.jejunu.myrealtrip.model.repository.RepositoryImpl
import kr.ac.jejunu.myrealtrip.model.service.HtmlService
import kr.ac.jejunu.myrealtrip.model.service.RssService
import kr.ac.jejunu.myrealtrip.ui.newsfragment.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel.NewsItemViewModel
import kr.ac.jejunu.myrealtrip.util.JsoupConverterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private val gson = GsonBuilder()
    .setLenient()
    .create()

var retrofit = module {
    val ok = OkHttpClient.Builder().connectTimeout(1,TimeUnit.MINUTES)
        .readTimeout(30,TimeUnit.SECONDS).build()
    single<RssService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(ok)
            .build()
            .create(RssService::class.java)
    }
    single<HtmlService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(JsoupConverterFactory)
            .client(ok)
            .build()
            .create(HtmlService::class.java)
    }
}

var model = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
    factory<HtmlDataModel> {
        HtmlDataModelImpl(get())
    }
}

var repository = module {
    single<Repository>{
        RepositoryImpl(get(),get())
    }
}

var viewModel = module {
    viewModel {
        NewsViewModel(get())
    }
//    viewModel {
//        NewsItemViewModel(get())
//    }
}

var adapter = module {
    factory {
        NewsAdapter(get())
    }
}

var modules = listOf(retrofit, model, viewModel, adapter, repository)