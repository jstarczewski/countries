package com.clakestudio.pc.countries.data.source.remote

import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class CountriesRestAdapter @Inject constructor(retrofit: Retrofit) {

    var countriesService: CountriesService

    init {
        countriesService = retrofit.create(CountriesService::class.java)
    }

    interface CountriesService {

        @GET(URLManager.all)
        fun getAllCountries(): Single<Response<List<ApiCountry>>>

        @GET(URLManager.name)
        fun getCountryByAlpha(@Path("alpha") alpha: String): Single<Response<ApiCountry>>

    }

    fun getAllCountries() = countriesService.getAllCountries()

    fun getCountryByAlpha(alpha: String) = countriesService.getCountryByAlpha(alpha)


}