package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable

class FakeCountriesRepository() : CountriesDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCountryByName(name: String): Flowable<ViewObject<Country>> = Flowable.just(ViewObject.success(CountriesDataProvider.provideColombia()))


}