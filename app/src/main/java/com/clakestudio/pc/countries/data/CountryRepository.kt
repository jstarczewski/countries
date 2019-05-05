package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountryDataSource
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountryRepository @Inject constructor(private val countriesRemoteDataSource: CountryDataSource) :
    CountryDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        return countriesRemoteDataSource.getAllCountries()
            .debounce(400, TimeUnit.MILLISECONDS)
    }

    override fun getCountryByName(name: String): Flowable<ViewObject<Country>> {
        return countriesRemoteDataSource.getCountryByName(name)
            .debounce(400, TimeUnit.MILLISECONDS)
    }


}