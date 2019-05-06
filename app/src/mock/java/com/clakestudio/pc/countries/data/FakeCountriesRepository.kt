package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable

class FakeCountriesRepository(private val asError: Boolean) : CountriesDataSource {

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        return if (!asError) Flowable.just(CountriesDataProvider.provideSampleCountriesWrapedAsSucces())
        else
            Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError())
    }

    override fun getCountryByName(alpha: String): Flowable<ViewObject<Country>> =
        if ("Colombia".toLowerCase().contains(alpha.toLowerCase()))
            Flowable.just(ViewObject.success(CountriesDataProvider.provideColombia()))
        else Flowable.just(ViewObject.success(CountriesDataProvider.providePoland()))


}