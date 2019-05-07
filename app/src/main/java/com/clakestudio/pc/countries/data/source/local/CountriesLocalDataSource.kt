package com.clakestudio.pc.countries.data.source.local

import io.reactivex.Single
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(private val countriesDao: CountryDao) {

    fun getAllCountries() = countriesDao.getAllCountries()

    fun getCountryByAlpha(alpha: String) : Single<Country> = countriesDao.getCountryByAlpha3Code(alpha)
        .onErrorResumeNext(Single.just(Country("null", "null", "null", "null", listOf())))

    fun saveCountry(country: Country) = countriesDao.saveCountry(country)

}