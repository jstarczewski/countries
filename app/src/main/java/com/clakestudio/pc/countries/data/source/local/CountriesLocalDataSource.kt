package com.clakestudio.pc.countries.data.source.local

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(private val countriesDao: CountryDao) : CountriesDataSource {

    override fun getAllCountries() = getAllCountriesFromLocalDataSource()

    override fun getCountryByAlpha(alpha: String) = getCountryByAlphaFromLocalDataSource(alpha)

    override fun saveCountry(country: Country) = countriesDao.saveCountry(
            DbCountry(
                    countryFlagUrl = country.countryFlagUrl,
                    countryName = country.countryName,
                    alpha3Code = country.alpha3Code,
                    details = country.countryDetails,
                    latlng = country.latlng.joinToString(separator = ",")
            )
    )

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