package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

@OpenForTesting
class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) :
        CountriesDataSource {


    override fun getAllCountries() = getAllCountriesFromRemoteDataSource()

    override fun getCountryByAlpha(alpha: String) = getCountryByAlphaFromRemoteDataSource(alpha)

    /**
     * Not implemented, not used, added to provide same interface for all data source
     * */

    override fun saveCountry(country: Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getAllCountriesFromRemoteDataSource(): Flowable<ViewObject<List<Country>>> =
            getCountriesFromRemoteDataSourceAndMap()
                    .toFlowable()

    /**
     * Here we check te response code of data. It is easier now to log the error
     * and check if it is programmers fault or API, suitable callback is passed within
     * to inform the user
     * */

    private fun <T> handleResponse(response: Response<T>): ViewObject<T> {
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


    /**
     * Data is provided from network unpacked from response and whether it has error or not
     * packed into viewObject wit suitable callback
     * */

    private fun getCountriesFromRemoteDataSourceAndMap(): Single<ViewObject<List<Country>>> =
            countriesRestAdapter.getAllCountries()
                    .map { data -> handleResponse(data) }
                    .map { viewObject ->
                        if (!viewObject.isHasError) {
                            ViewObject.success(viewObject.data!!.map {
                                Country(it)
                            }, true)
                        } else {
                            ViewObject.error(viewObject.errorMessage!!, null)
                        }

                    }

    /**
     * Same flow for a single country's data
     * */

    private fun getCountryByAlphaFromRemoteDataSource(alpha: String): Flowable<ViewObject<Country>> =
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