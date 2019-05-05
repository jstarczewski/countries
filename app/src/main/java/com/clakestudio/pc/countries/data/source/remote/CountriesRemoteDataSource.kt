package com.clakestudio.pc.countries.data.source.remote

import android.util.Log
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import retrofit2.Response
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) :
    CountriesDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        countriesRestAdapter.getAllCountries()
            .flatMapPublisher {
                Flowable.just(handleResponse(it))
            }
    /*
    countriesRestAdapter.getAllCountries()
        .toFlowable()
        .materialize()
        .map {
            if (it.isOnErr
        }
*/


    override fun getCountryByName(name: String): Flowable<ViewObject<Country>> =
        countriesRestAdapter.getCountryByName(name).flatMapPublisher {
            Flowable.just(handleResponse(it))
        }

    private fun <T> handleResponse(response: Response<T>): ViewObject<T> {
        Log.e("Response", response.code().toString())
        if (response.isSuccessful) {
            if (response.body() == null || response.code() == 204)
                return ViewObject.error("Response is empty", response.body())
            return ViewObject.success(response.body()!!)
        } else {
            return ViewObject.error(
                "Error number: " +
                        response.code().toString() + " occured \nWith body " + response.body().toString(),
                response.body()
            )
        }
    }

}