package com.clakestudio.pc.countries.data

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Path


interface CountryDataSource {

    fun getAllCountries(): Flowable<List<Country>>

    fun getCountryByName(@Path("name") name: String): Flowable<Country>

}