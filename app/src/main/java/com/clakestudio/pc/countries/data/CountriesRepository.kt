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
            getAllUsersFromRemoteDataSource(),
            getAllUsersFromLocalDataSource()
        )
            .debounce(400, TimeUnit.MILLISECONDS)


    private fun getAllUsersFromRemoteDataSource(): Flowable<ViewObject<List<Country>>> =
        getCountriesFromRemoteDataSourceAndMap()
            .toFlowable()
            .doOnNext { countries ->
                countries.data?.forEach {
                    countriesLocalDataSource.saveCountry(it)
                }
            }

    private fun getAllUsersFromLocalDataSource(): Flowable<ViewObject<List<Country>>> =
        countriesLocalDataSource.getAllCountries()
            .map { countries ->
                ViewObject.success(countries.map {
                    Country(it.countryName, it.countryFlagUrl, it.alpha3Code, it.latlng, it.details)
                })
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