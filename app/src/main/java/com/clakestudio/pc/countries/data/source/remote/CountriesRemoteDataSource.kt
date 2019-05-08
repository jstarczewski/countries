package com.clakestudio.pc.countries.data.source.remote

import android.util.Log
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) :
    CountriesDataSource {


    override fun getAllCountries() = getAllCountriesFromRemoteDataSource()


    override fun getCountryByAlpha(alpha: String) = getCountryByAlphaFromRemoteDataSource(alpha)


    fun getAllCountriesFromRemoteDataSource(): Flowable<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>> =
        getCountriesFromRemoteDataSourceAndMap()
            .toFlowable()

           // .startWith(ViewObject.loading(null))

    override fun saveCountry(country: com.clakestudio.pc.countries.ui.details.Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun <T> handleResponse(response: Response<T>): ViewObject<T> {
        Log.e("Response", response.code().toString())
        if (response.isSuccessful) {
            if (response.body() == null || response.code() == 204)
                return ViewObject.error("Response is empty", response.body())
            return ViewObject.success(response.body()!!, true)
        } else {
            return ViewObject.error(
                "Error number: " +
                        response.code().toString() + " occured \nWith body " + response.body().toString(),
                response.body()
            )
        }
    }

    fun getCountriesFromRemoteDataSourceAndMap(): Single<ViewObject<List<com.clakestudio.pc.countries.ui.details.Country>>> =
        countriesRestAdapter.getAllCountries()
            .map { data -> handleResponse(data) }
            .map { viewObject ->
                if (!viewObject.isHasError) {
                    ViewObject.success(viewObject.data!!.map {
                        com.clakestudio.pc.countries.ui.details.Country(it)
                    }, true)
                } else {
                    ViewObject.error(viewObject.errorMessage!!, null)
                }

            }

    fun getCountryByAlphaFromRemoteDataSource(alpha: String): Flowable<ViewObject<com.clakestudio.pc.countries.ui.details.Country>> =
        countriesRestAdapter.getCountryByAlpha(alpha).map {
            handleResponse(it)
        }
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
}