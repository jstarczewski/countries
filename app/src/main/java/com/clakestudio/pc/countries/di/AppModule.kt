package com.clakestudio.pc.countries.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.data.source.local.CountriesDatabase
import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.local.CountryDao
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRestAdapter
import com.clakestudio.pc.countries.data.source.remote.URLManager
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.SchedulersProvider
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
    fun provideCountriesDatabase(app: Application): CountriesDatabase {
        return Room.databaseBuilder(app, CountriesDatabase::class.java, "countries.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountriesDao(countriesDatabase: CountriesDatabase): CountryDao {
        return countriesDatabase.countryDao()
    }


    @Provides
    @Singleton
    fun provideAppSchedulers(): SchedulersProvider {
        return AppSchedulersProvider()
    }

    @Provides
    @Singleton
    fun provideCountryRemoteDataSource(countriesRestAdapter: CountriesRestAdapter): CountriesRemoteDataSource {
        return CountriesRemoteDataSource(countriesRestAdapter)
    }

    @Singleton
    @Provides
    fun provideCountriesLocalDataSource(countriesDao: CountryDao): CountriesLocalDataSource {
        return CountriesLocalDataSource(countriesDao)
    }


    @Provides
    @Singleton
    fun provideCountryRepository(
        countriesRemoteDataSource: CountriesRemoteDataSource,
        countriesLocalDataSource: CountriesLocalDataSource
    ): CountriesDataSource {
        return CountriesRepository(countriesRemoteDataSource, countriesLocalDataSource)
    }


}