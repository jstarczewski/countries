package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.source.remote.URLManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWithFakeInterceptorInjection {

    fun provideRetrofitWithRxAdapterFactory(interceptor: FakeInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URLManager.base)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    fun provideRetrofitWithoutRxAdapterFactory(interceptor: FakeInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URLManager.base)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}