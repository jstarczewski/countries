package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class CountriesRepository @Inject constructor(private val countriesRemoteDataSource: CountriesDataSource) :
    CountriesDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        return countriesRemoteDataSource.getAllCountries()
            .debounce(400, TimeUnit.MILLISECONDS)
    }

    override fun getCountryByName(name: String): Flowable<ViewObject<Country>> {
        return countriesRemoteDataSource.getCountryByName(name)
            .debounce(400, TimeUnit.MILLISECONDS)
    }


}