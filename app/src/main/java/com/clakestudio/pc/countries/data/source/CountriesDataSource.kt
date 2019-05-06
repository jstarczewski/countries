package com.clakestudio.pc.countries.data.source

import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import retrofit2.http.Path


interface CountriesDataSource {

    fun getAllCountries(): Flowable<ViewObject<List<Country>>>

    fun getCountryByName(@Path("name") alpha: String): Flowable<ViewObject<Country>>


}