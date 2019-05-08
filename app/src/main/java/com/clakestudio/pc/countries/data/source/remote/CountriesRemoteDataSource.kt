package com.clakestudio.pc.countries.data.source.remote

import android.util.Log
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) {

    fun getAllCountries(): Single<ViewObject<List<Country>>> =
        countriesRestAdapter.getAllCountries()
            .map { data -> handleResponse(data) }


    fun getCountryByAlpha(alpha: String): Single<ViewObject<Country>> =
        countriesRestAdapter.getCountryByAlpha(alpha).map {
            handleResponse(it)
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

}