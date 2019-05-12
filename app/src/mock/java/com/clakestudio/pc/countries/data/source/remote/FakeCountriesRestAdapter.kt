package com.clakestudio.pc.countries.data.source.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class FakeCountriesRestAdapter(retrofit: Retrofit) {

    /**
     * FakeCountriesRestAdapter used for early stage tests of handleResponse fun that
     * was crucial to future development of project
     * */

    private val countriesService: CountriesService

    init {
        countriesService = retrofit.create(CountriesService::class.java)
    }

    interface CountriesService {

        @GET(URLManager.all)
        fun getAllCountries(): Call<Response<List<ApiCountry>>>

        @GET(URLManager.name)
        fun getCountryByName(@Path("alpha") name: String): Call<Response<ApiCountry>>

    }

    fun getAllCountries() = countriesService.getAllCountries()

    fun getCountryByName(name: String) = countriesService.getCountryByName(name)


}