package com.clakestudio.pc.countries.data.remote

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.CountryDataSource
import io.reactivex.Single
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val countriesRestAdapter: CountriesRestAdapter) : CountryDataSource {

    override fun getAllCountries(): Single<List<Country>> = countriesRestAdapter.getAllCountries().flatMap {

    }

    override fun getCountryByName(name: String): Single<Country> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}