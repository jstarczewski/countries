package com.clakestudio.pc.countries.data.source

import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable

/**
 * Same interface for all country data sources, could be transformed to generic
 * */

interface CountriesDataSource {

    fun getAllCountries(): Flowable<ViewObject<List<Country>>>

    fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>>

    fun saveCountry(country: Country)

}