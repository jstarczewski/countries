package com.clakestudio.pc.countries.data.remote

import com.clakestudio.pc.countries.data.Country
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class CountriesRestAdapter @Inject constructor(retrofit: Retrofit) {

    var countriesService: CountriesService

    init {
        countriesService = retrofit.create(CountriesService::class.java)
    }

    interface CountriesService {

        @GET(URLManager.all)
        fun getAllCountries(): Single<Response<List<Country>>>

        @GET(URLManager.name)
        fun getCountryByName(name: String) : Single<Response<Country>>


    }

    fun getAllCountries() = countriesService.getAllCountries()

    fun getCountryByName(name: String) = countriesService.getCountryByName(name)


}