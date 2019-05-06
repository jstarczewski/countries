package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class CountriesRepository @Inject constructor(private val countriesRemoteDataSource: CountriesRemoteDataSource) :
    CountriesDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        return countriesRemoteDataSource.getAllCountries()
        // .debounce(400, TimeUnit.MILLISECONDS)
    }

    override fun getCountryByName(alpha: String): Flowable<ViewObject<Country>> {
        return countriesRemoteDataSource.getCountryByName(alpha)
            .debounce(400, TimeUnit.MILLISECONDS)
    }

    fun mergeAllCountries() = Flowable.concatArrayEager(
        testGetAllCountries().toFlowable(),
        testGetAllCountriesTwo().toFlowable()
    ).debounce(400, TimeUnit.MILLISECONDS)


    fun testGetAllCountriesTwo(): Single<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>> =
        countriesRemoteDataSource.getAllCountries()
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        com.clakestudio.pc.countries.ui.details.Country(it)
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

    fun testGetAllCountries(): Single<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>> =
        countriesRemoteDataSource.getAllCountries()
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        com.clakestudio.pc.countries.ui.details.Country(it)
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