package com.clakestudio.pc.countries.data.source.local

import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(private val countriesDao: CountryDao) : CountriesDataSource {

    override fun getAllCountries() = getAllCountriesFromLocalDataSource()

    override fun getCountryByAlpha(alpha: String) = getCountryByAlphaFromLocalDataSource(alpha)

    /**
     * Data is saved and mapped for dbCountry, loaded when is was impossible to fetch from network
     * */

    override fun saveCountry(country: Country) = countriesDao.saveCountry(
            DbCountry(
                    countryFlagUrl = country.countryFlagUrl,
                    countryName = country.countryName,
                    alpha3Code = country.alpha3Code,
                    details = country.countryDetails,
                    latlng = country.latlng.joinToString(separator = ",")
            )
    )

    /**
     * Data loaded from local db, with suitable callback when there was none
     * */

    private fun getAllCountriesFromLocalDataSource(): Flowable<ViewObject<List<Country>>> =
            countriesDao.getAllCountries()
                    .map { countries ->
                        if (countries.isNotEmpty()) {
                            ViewObject.success(countries.map {
                                Country(it)
                            }, false)
                        } else {
                            ViewObject.error(
                                    "Cannot fetch data from network, no cache is available, check your connection and swipe to refresh",
                                    null
                            )
                        }
                    }.toFlowable()

    /**
     * The onErrorResumeNext is needed because Single returns an error when there is no country to load
     * The "null" country is then emitted and transformed into ViewObject with information about occurred error
     * */

    private fun getCountryByAlphaFromLocalDataSource(alpha: String): Flowable<ViewObject<Country>> =
            countriesDao.getCountryByAlpha3Code(alpha)
                    .onErrorResumeNext(Single.just(DbCountry("null", "null", "null", "null", listOf())))
                    .map { country ->
                        if (country.alpha3Code.isNotEmpty() && country.alpha3Code != "null") {
                            ViewObject.success(
                                Country(country),
                                    false
                            )
                        } else {
                            ViewObject.error(
                                    "Cannot fetch data from network, no cache is available, check your connection and swipe to refresh",
                                    null
                            )
                        }
                    }.toFlowable()
}