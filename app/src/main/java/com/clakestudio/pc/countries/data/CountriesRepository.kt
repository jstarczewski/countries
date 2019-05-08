package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.data.source.remote.RemoteDataUnavailableException
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Notification
import io.reactivex.Single
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class CountriesRepository @Inject constructor(
    private val countriesRemoteDataSource: CountriesDataSource,
    private val countriesLocalDataSource: CountriesDataSource
) : CountriesDataSource {


    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        countriesRemoteDataSource.getAllCountries()
           // .startWith(ViewObject.loading(null))
            .doOnNext { countries ->
                countries.data?.forEach {
                    countriesLocalDataSource.saveCountry(it)
                }
            }
            .map {
                if (it.isHasError)
                    throw RemoteDataUnavailableException(it.errorMessage ?: "Data fetch error")
                it
            }.onErrorResumeNext(countriesLocalDataSource.getAllCountries())


    override fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>> =
        countriesRemoteDataSource.getCountryByAlpha(alpha).map {
            if (it.isHasError)
                throw RemoteDataUnavailableException(it.errorMessage ?: "Error occured")
            it
        }.onErrorResumeNext(countriesLocalDataSource.getCountryByAlpha(alpha))

    override fun saveCountry(country: Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*
    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        Flowable.concatArrayEagerDelayError(
            getAllCountriesFromLocalDataSource(),
            getAllCountriesFromRemoteDataSource()
                .materialize()
                .map { notification ->
                    if (notification.isOnError)
                        ViewObject.error(notification.error!!.localizedMessage, null)
                    notification
                }
               /* .filter {
                    !it.isOnError
                }*/
                .dematerialize { it }
                .debounce(500, TimeUnit.MILLISECONDS)
        )
        */

/*
    fun getAllCountriesFromRemoteDataSource(): Flowable<ViewObject<List<Country>>> =
        getCountriesFromRemoteDataSourceAndMap()
            .toFlowable()
            .startWith(ViewObject.loading(null))
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
         }*/
/*
    private fun getAllCountriesFromLocalDataSource(): Flowable<ViewObject<List<Country>>> =
        countriesLocalDataSource.getAllCountries()
            .map { countries ->
                if (countries.isNotEmpty()) {
                    ViewObject.success(countries.map {
                        Country(it.countryName, it.alpha3Code, it.countryFlagUrl, it.latlng.split(","), it.details)
                    }, false)
                } else {
                    ViewObject.error(
                        "Cannot fetch data from network, no cache is available, check your connection and swipe to refresh",
                        null
                    )
                }
            }.toFlowable().startWith(ViewObject.loading(null))*/


    /*
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
                .dematerialize { it }
                .debounce(5, TimeUnit.SECONDS)
        )
*/
    /*
    private fun getCountryByAlphaFromLocalDataSource(alpha: String): Flowable<ViewObject<Country>> =
        countriesLocalDataSource.getCountryByAlpha(alpha)
            .map { country ->
                if (country.alpha3Code.isNotEmpty() && country.alpha3Code != "null") {
                    ViewObject.success(
                        Country(
                            country.countryName,
                            country.alpha3Code,
                            country.countryFlagUrl,
                            country.latlng.split(","),
                            country.details
                        ),
                        false
                    )
                } else {
                    ViewObject.error("Cannot fetch data from network, no cache is available", null)
                }
            }.toFlowable()
*/


    /*
    private fun getCountryByAlphaFromRemoteDataSource(alpha: String): Flowable<ViewObject<Country>> =
        countriesRemoteDataSource.getCountryByAlpha(alpha)
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(
                        Country(viewObject.data!!),
                        true
                    )
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }
            }.toFlowable()
*/
/*
    private fun getCountriesFromRemoteDataSourceAndMap(): Single<ViewObject<List<Country>>> =
        countriesRemoteDataSource.getAllCountries()
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        Country(it)
                    }, true)
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }

            }

*/
}