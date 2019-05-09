package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.FakeInterceptor
import com.clakestudio.pc.countries.util.RetrofitWithFakeInterceptroInjection
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test

class CountriesRemoteDataSourceTest {

    private val testSubscriberList = TestSubscriber<List<Country>>()
    private val testSubscriberViewObjectList = TestSubscriber<ViewObject<List<Country>>>()

    private val testSubscriberCountry = TestSubscriber<Country>()
    private val testSubscriberViewObjectCountry = TestSubscriber<ViewObject<Country>>()

    private val alpha = "POL"

    private val fakeInterface = FakeInterceptor()
    private val countriesRestAdapter = CountriesRestAdapter(
        RetrofitWithFakeInterceptroInjection.provideRetrofitWithRxAdapterFactory(
            FakeInterceptor()
        )
    )
    private val remoteDataSource = CountriesRemoteDataSource(countriesRestAdapter)

    @Test
    fun getAllCountriesWhenResponseCodeIs200AssertValues() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
            .data
        fakeInterface.responseCode = 200
        remoteDataSource.getAllCountries()
            .map { it.data }
            .subscribe(testSubscriberList)
        testSubscriberList.assertValues(expectedTestData)
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs404AssertValues() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsError()
        fakeInterface.responseCode = 404
        remoteDataSource.getAllCountries()
            .map { it.data }
            .subscribe(testSubscriberList)
        testSubscriberList.assertValues(expectedTestData.data)
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs404() {
        // val expectedTestData = ViewObject.error("Test error", listOf(CountriesDataProvider.providePoland()).map {
        //    Country(it)
        // })
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsError()
        fakeInterface.responseCode = 404
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { expectedTestData.isHasError }
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs204() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsError()
        fakeInterface.responseCode = 204
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { it.isHasError }
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs200() {
        fakeInterface.responseCode = 200
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue{!it.isHasError}
    }

    @Test
    fun getCountryWhenResponseCodeIs200AssertValues() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsSuccess()
            .data
        //val expectedTestData = listOf(Country(CountriesDataProvider.providePoland()))
        fakeInterface.responseCode = 200
        remoteDataSource.getCountryByAlpha(alpha)
            .map { it.data }
            .subscribe(testSubscriberCountry)
        testSubscriberCountry.assertValues(expectedTestData)
    }

    @Test
    fun getCountryWhenResponseCodeIs404AssertValues() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsSuccess()
        fakeInterface.responseCode = 404
        remoteDataSource.getCountryByAlpha("POL")
            .map { it.data }
            .subscribe(testSubscriberCountry)
        testSubscriberCountry.assertValues(expectedTestData.data)
    }

    @Test
    fun getCountryWhenResponseCodeIs404() {
        // val expectedTestData = ViewObject.error("Test error", listOf(CountriesDataProvider.providePoland()).map {
        //    Country(it)
        // })
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsError()
        fakeInterface.responseCode = 404
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { expectedTestData.isHasError }
    }

    @Test
    fun getCountryWhenResponseCodeIs204() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsError()
        fakeInterface.responseCode = 204
        remoteDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { expectedTestData.isHasError }
    }

    @Test
    fun getCountryWhenResponseCodeIs200() {
        fakeInterface.responseCode = 200
        remoteDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue{!it.isHasError}
    }


}