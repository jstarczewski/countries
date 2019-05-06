package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.vo.ViewObject
import retrofit2.Response


class SyncRemoteDataSource(private val fakeCountriesRestAdapter: FakeCountriesRestAdapter) {

    fun getAllCountries() = fakeCountriesRestAdapter.getAllCountries()

    fun getCountryByName(name: String) = fakeCountriesRestAdapter.getCountryByName(name)

    fun <T> handleResponse(response: Response<T>): ViewObject<T> {
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