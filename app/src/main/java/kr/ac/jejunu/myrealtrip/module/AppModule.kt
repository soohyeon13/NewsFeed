package kr.ac.jejunu.myrealtrip.module

import com.google.gson.GsonBuilder
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import kr.ac.jejunu.myrealtrip.data.repository.RepositoryImpl
import kr.ac.jejunu.myrealtrip.data.repository.SearchRepositoryImpl
import kr.ac.jejunu.myrealtrip.data.repository.CategoryRepositoryImpl
import kr.ac.jejunu.myrealtrip.data.service.HtmlService
import kr.ac.jejunu.myrealtrip.data.service.NewsCategoryService
import kr.ac.jejunu.myrealtrip.data.service.RssService
import kr.ac.jejunu.myrealtrip.data.service.SearchService
import kr.ac.jejunu.myrealtrip.domain.repository.CategoryRepository
import kr.ac.jejunu.myrealtrip.domain.repository.SearchRepository
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.cate.tab.business.BusinessViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.entertainment.EntertainViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.general.GeneralViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.health.HealthViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.recent_news.RecentNewsViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.science.ScienceViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.sports.SportsViewModel
import kr.ac.jejunu.myrealtrip.ui.cate.tab.technology.TechnologyViewModel
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import kr.ac.jejunu.myrealtrip.ui.splash.viewmodel.SplashViewModel
import kr.ac.jejunu.myrealtrip.util.HttpClientService
import kr.ac.jejunu.myrealtrip.util.JsoupConverterFactory
import kr.ac.jejunu.myrealtrip.util.ResponseInterceptor
import kr.ac.jejunu.myrealtrip.util.SearchClient
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

val client = OkHttpClient
    .Builder()
    .readTimeout(120, TimeUnit.SECONDS)
    .writeTimeout(120, TimeUnit.SECONDS)
    .connectTimeout(120, TimeUnit.SECONDS)
    .addInterceptor(ResponseInterceptor())
    .build()
val gson = GsonBuilder()
    .setDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
    .create()

var dataModules = module {
    single<Repository> { RepositoryImpl(get(), get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
    single<CategoryRepository> {
        CategoryRepositoryImpl(
            get()
        )
    }
    single<RssService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(HttpClientService.getUnsafeOkHttpClient())
            .build()
            .create(RssService::class.java)
    }
    single<HtmlService> {
        Retrofit.Builder()
            .baseUrl("https://news.google.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(JsoupConverterFactory)
            .client(HttpClientService.getUnsafeOkHttpClient())
            .build()
            .create(HtmlService::class.java)
    }
    single<SearchService> {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(SearchClient.httpClient())
            .build()
            .create(SearchService::class.java)
    }
    single<NewsCategoryService> {
        Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(NewsCategoryService::class.java)
    }
}
var viewModelModules = module {
    viewModel { NewsViewModel(get(), get(),get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { BusinessViewModel(get()) }
    viewModel { EntertainViewModel(get()) }
    viewModel { GeneralViewModel(get()) }
    viewModel { HealthViewModel(get()) }
    viewModel { RecentNewsViewModel(get()) }
    viewModel { ScienceViewModel(get()) }
    viewModel { SportsViewModel(get()) }
    viewModel { TechnologyViewModel(get()) }
}
var adapterModules = module {
    factory { NewsAdapter() }
}
var modules = listOf(dataModules, viewModelModules, adapterModules)