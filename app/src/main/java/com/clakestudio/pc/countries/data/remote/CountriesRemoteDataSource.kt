package com.clakestudio.pc.countries.data.remote

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.CountryDataSource
import io.reactivex.Flowable
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) : CountryDataSource {

    override fun getAllCountries(): Flowable<List<Country>> = countriesRestAdapter.getAllCountries().flatMapPublisher { response ->
        if (response.isSuccessful) return@flatMapPublisher Flowable.just(response.body())
        else return@flatMapPublisher Flowable.just(null)
    }

    override fun getCountryByName(name: String): Flowable<Country> = countriesRestAdapter.getCountryByName(name).flatMapPublisher { response ->
        if (response.isSuccessful) return@flatMapPublisher Flowable.just(response.body())
        else return@flatMapPublisher Flowable.just(null)

    }
}