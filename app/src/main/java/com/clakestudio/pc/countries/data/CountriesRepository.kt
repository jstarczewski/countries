package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class CountriesRepository @Inject constructor(
    private val countriesRemoteDataSource: CountriesRemoteDataSource,
    private val countriesLocalDataSource: CountriesLocalDataSource
) :
    CountriesDataSource {

    override fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>> =
        Flowable.concatArrayEager(
            getCountryByAlphaFromLocalDataSource(alpha),
            getCountryByAlphaFromRemoteDataSource(alpha)
                .materialize()
                .map { notification ->
                    if (notification.isOnError)
                        ViewObject.error(notification.error!!.localizedMessage, null)
                    notification
                }
                .filter {
                    !it.isOnError
                }
                .dematerialize<ViewObject<Country>>()
                .debounce(400, TimeUnit.MILLISECONDS)
        )


    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        Flowable.concatArrayEager(
            getAllCountriesFromLocalDataSource(),
            getAllCountriesFromRemoteDataSource()
                .materialize()
                .map { notification ->
                    if (notification.isOnError)
                        ViewObject.error(notification.error!!.localizedMessage, null)
                    notification
                }
                .filter {
                    !it.isOnError
                }
                .dematerialize<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>>()
                .debounce(400, TimeUnit.MILLISECONDS)
        )


    private fun getCountryByAlphaFromLocalDataSource(alpha: String): Flowable<ViewObject<Country>> =
        countriesLocalDataSource.getCountryByAlpha(alpha)
            .map { country ->
                if (country.alpha3Code.isNotEmpty() && country.alpha3Code != "null") {
                    ViewObject.success(
                        Country(
                            country.countryName,
                            country.countryFlagUrl,
                            country.alpha3Code,
                            country.latlng.split(","),
                            country.details
                        )
                    )
                } else {
                    ViewObject.error("Cannot fetch data from network, no cache is available", null)
                }
            }.toFlowable()

    private fun getCountryByAlphaFromRemoteDataSource(alpha: String): Flowable<ViewObject<Country>> =
        countriesRemoteDataSource.getCountryByAlpha(alpha)
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(Country(viewObject.data!!))
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }
            }.toFlowable()

    fun getAllCountriesFromRemoteDataSource(): Flowable<ViewObject<List<Country>>> =
        getCountriesFromRemoteDataSourceAndMap()
            .toFlowable()
            .doOnNext { countries ->
                countries.data?.forEach {
                    countriesLocalDataSource.saveCountry(
                        com.clakestudio.pc.countries.data.source.local.Country(
                            it.alpha3Code,
                            it.countryName,
                            it.countryFlagUrl,
                            it.latlng.joinToString(separator = ","),
                            it.countryDetails
                        )
                    )
                }
            }

    private fun getAllCountriesFromLocalDataSource(): Flowable<ViewObject<List<Country>>> =
        countriesLocalDataSource.getAllCountries()
            .map { countries ->
                if (countries.isNotEmpty()) {
                    ViewObject.success(countries.map {
                        Country(it.countryName, it.countryFlagUrl, it.alpha3Code, it.latlng.split(","), it.details)
                    })
                } else {
                    ViewObject.error("Country is empty", null)
                }
            }
            .toFlowable()


    private fun getCountriesFromRemoteDataSourceAndMap(): Single<ViewObject<List<Country>>> =
        countriesRemoteDataSource.getAllCountries()
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        Country(it)
                    })
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }

            }


}