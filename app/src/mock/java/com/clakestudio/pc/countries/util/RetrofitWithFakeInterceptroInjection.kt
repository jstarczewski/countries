package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.source.remote.FakeInterceptor
import com.clakestudio.pc.countries.data.source.remote.URLManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitWithFakeInterceptroInjection {

    fun provideRetrofit(interceptor: FakeInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient()
            okHttpClient.interceptors().add(interceptor)
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URLManager.base)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }
}