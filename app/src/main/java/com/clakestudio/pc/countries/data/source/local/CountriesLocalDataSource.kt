package com.clakestudio.pc.countries.data.source.local

import androidx.room.EmptyResultSetException
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Single
import io.reactivex.SingleSource
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(private val countriesDao: CountryDao) {

    fun getAllCountries() = countriesDao.getAllCountries()

    fun getCountryByAlpha(alpha: String) = countriesDao.getCountryByAlpha3Code(alpha)
        .onErrorResumeNext(Single.just(Country("null", "null", "null", "null", listOf())))

    fun saveCountry(country: Country) = countriesDao.saveCountry(country)

    /*
    (res ->
    {
        if (res == null) {
            Single.just(ViewObject.error("Empty dataset", res))
        } else {
            Single.just(ViewObject.success(res))
        }
    })*/

}