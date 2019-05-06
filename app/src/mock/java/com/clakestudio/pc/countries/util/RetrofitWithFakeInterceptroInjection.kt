package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.source.remote.URLManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWithFakeInterceptroInjection {

    fun provideRetrofit(interceptor: FakeInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URLManager.base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}