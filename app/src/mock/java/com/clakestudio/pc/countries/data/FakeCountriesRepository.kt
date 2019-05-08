package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable

class FakeCountriesRepository(private val asError: Boolean) : CountriesDataSource {

    /**
     * Now the fake countries repository is useless, because final CountriesRepository provides small and simple interface
     * that can be easily mocked. This class was used when I tested viewmodels after implementing their basic functionality.
     *
     * */

    override fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>> =
        if ("Colombia".toLowerCase().contains(alpha.toLowerCase()))
            Flowable.just(ViewObject.success(Country(CountriesDataProvider.provideColombia()), true))
        else Flowable.just(ViewObject.success(Country(CountriesDataProvider.providePoland()), true))

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> {
        return if (!asError) Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess())
        else
            Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError())
    }


}