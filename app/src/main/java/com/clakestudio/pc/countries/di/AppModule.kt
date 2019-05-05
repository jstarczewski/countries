package com.clakestudio.pc.countries.di

import android.app.Application
import com.clakestudio.pc.countries.data.source.CountryDataSource
import com.clakestudio.pc.countries.data.CountryRepository
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRestAdapter
import com.clakestudio.pc.countries.data.source.remote.URLManager
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(/*application: Application*/): Retrofit {
/*
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }*/

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
     //       .addNetworkInterceptor(networkCacheInterceptor)
       //     .cache(cache)
            .retryOnConnectionFailure(true)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URLManager.base)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryRemoteDataSource(countriesRestAdapter: CountriesRestAdapter) : CountriesRemoteDataSource {
        return CountriesRemoteDataSource(countriesRestAdapter)
    }


    @Provides
    @Singleton
    fun provideCountryRepository(countriesRemoteDataSource: CountriesRemoteDataSource) : CountryDataSource {
        return CountryRepository(countriesRemoteDataSource)
    }


}