package com.clakestudio.pc.countries.di

import com.clakestudio.pc.countries.data.CountryDataSource
import com.clakestudio.pc.countries.data.CountryRepository
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.data.remote.CountriesRestAdapter
import com.clakestudio.pc.countries.data.remote.URLManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
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