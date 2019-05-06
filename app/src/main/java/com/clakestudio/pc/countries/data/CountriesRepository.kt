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

    override fun getCountryByName(alpha: String): Flowable<ViewObject<Country>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        Flowable.concatArrayEager(
            getAllUsersFromLocalDataSource(),
            getAllUsersFromRemoteDataSource()
                .materialize()
                .map { notification ->
                    if (notification.isOnError)
                        ViewObject.error(notification.error!!.localizedMessage, null)
                    notification

                    /*
                    if (it.isOnError) {
                       // _error.value = (it.error?.localizedMessage + "\nSwipe to refresh")
                       // _loading.value = false
                    }
                    it*/
                }
                .filter {
                    !it.isOnError
                }
                .dematerialize<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>>()
                .debounce(400, TimeUnit.MILLISECONDS)
        )


    private fun getAllUsersFromRemoteDataSource(): Flowable<ViewObject<List<Country>>> =
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

    private fun getAllUsersFromLocalDataSource(): Flowable<ViewObject<List<Country>>> =
        countriesLocalDataSource.getAllCountries()
            .map { countries ->
                if (countries.isNotEmpty()) {
                    ViewObject.success(countries.map {
                        Country(it.countryName, it.countryFlagUrl, it.alpha3Code, it.latlng.split(","), it.details)
                    })
                }
                else {
                    ViewObject.error("Country is empty", null)
                }
            }
            .toFlowable()


    /* if (!viewObject.isHasError) {
         //ViewObject.success(
             viewObject.data?.map {
                 com.clakestudio.pc.countries.ui.details.Country(it)
         }
         )
     }*/


    //   fun mergeAllCountries() = Flowable.concatArrayEager(
    //      testGetAllCountries().toFlowable(),
    // testGetAllCountriesTwo().toFlowable()
    //   ).debounce(400, TimeUnit.MILLISECONDS)


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
                /* if (!viewObject.isHasError) {
                     //ViewObject.success(
                         viewObject.data?.map {
                             com.clakestudio.pc.countries.ui.details.Country(it)
                     }
                     )
                 }*/
            }

    fun testGetAllCountries(): Single<ViewObject<List<Country>>> =
        countriesRemoteDataSource.getAllCountries()
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        Country(it)
                    })
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }
                /* if (!viewObject.isHasError) {
                     //ViewObject.success(
                         viewObject.data?.map {
                             com.clakestudio.pc.countries.ui.details.Country(it)
                     }
                     )
                 }*/
            }

}