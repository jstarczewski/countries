package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.CountryDataSource
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) :
    CountryDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
        countriesRestAdapter.getAllCountries()
            .flatMapPublisher { response ->
                return@flatMapPublisher Flowable.just(handleResponse(response))

            }
    /*
    countriesRestAdapter.getAllCountries()
        .toFlowable()
        .materialize()
        .map {
            if (it.isOnErr
        }
*/


    override fun getCountryByName(name: String): Flowable<Country> =
        countriesRestAdapter.getCountryByName(name).flatMapPublisher { response ->
            if (response.isSuccessful) return@flatMapPublisher Flowable.just(response.body())
            else return@flatMapPublisher Flowable.just(null)

        }

    private fun handleResponse(response: Response<List<Country>>): ViewObject<List<Country>> {
        if (response.isSuccessful) {
            if (response.body()!!.isEmpty())
                return ViewObject.error("empty", response.body())
            return ViewObject.succes(response.body()!!)
        } else {
            return ViewObject.error(response.errorBody().toString(), response.body())
        }
    }

}