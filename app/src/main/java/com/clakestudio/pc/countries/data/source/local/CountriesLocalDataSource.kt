package com.clakestudio.pc.countries.data.source.local

import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(private val countriesDao: CountryDao) {

    fun getAllCountries() = countriesDao.getAllCountries()

    fun getCountryByAlphaCode(alpha: String) = countriesDao.getCountryByAlpha3Code(alpha)

    fun saveCountry(country: Country) = countriesDao.saveCountry(country)

}