package kr.ac.jejunu.myrealtrip.module

import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.data.repository.RepositoryImpl
import kr.ac.jejunu.myrealtrip.data.service.HtmlService
import kr.ac.jejunu.myrealtrip.data.service.RssService
import kr.ac.jejunu.myrealtrip.ui.news.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.ui.splash.viewmodel.SplashViewModel
import kr.ac.jejunu.myrealtrip.util.JsoupConverterFactory
import kr.ac.jejunu.myrealtrip.util.ResponseInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val client = OkHttpClient
    .Builder()
    .addInterceptor(ResponseInterceptor())
    .build()
var retrofit = module {
    single<RssService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(client)
            .build()
            .create(RssService::class.java)
    }
    single<HtmlService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(JsoupConverterFactory)
            .client(client)
            .build()
            .create(HtmlService::class.java)
    }
}

var repository = module {
    single<Repository> {
        RepositoryImpl(get(), get())
    }
}
var newsViewModel = module {
    viewModel {
        NewsViewModel(get())
    }
}
var splashViewModel = module {
    viewModel {
        SplashViewModel(get())
    }
}
var newsAdapter = module {
    factory { NewsAdapter() }
}
var modules = listOf(repository, retrofit, newsViewModel, newsAdapter, splashViewModel)