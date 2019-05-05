package com.clakestudio.pc.countries.data

import io.reactivex.Flowable
import javax.inject.Inject

class CountryRepository @Inject constructor(private val countriesRemoteDataSource: CountryDataSource) :
    CountryDataSource {

    override fun getAllCountries(): Flowable<List<Country>> {
        return countriesRemoteDataSource.getAllCountries()
    }

    override fun getCountryByName(name: String): Flowable<Country> {
        return countriesRemoteDataSource.getCountryByName(name)
    }


}