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
    private val errorSubscriber = TestSubscriber<Boolean>()

    private val alpha = "POL"

    private val fakeInterceptor = FakeInterceptor(404)
    private val countriesRestAdapter = CountriesRestAdapter(RetrofitWithFakeInterceptroInjection.provideRetrofitWithRxAdapterFactory(fakeInterceptor))
    private val remoteDataSource = CountriesRemoteDataSource(countriesRestAdapter)

    @Test
    fun getAllCountriesWhenResponseCodeIs200AssertValues() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
            .data
        fakeInterceptor.responseCode = 200
        remoteDataSource.getAllCountries()
            .map { it.data }
            .subscribe(testSubscriberList)
        testSubscriberList.assertValues(expectedTestData)
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs404() {
        fakeInterceptor.responseCode = 404
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { it.isHasError }
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs204() {
        fakeInterceptor.responseCode = 204
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { it.errorMessage == "Response is empty" }
    }

    @Test
    fun getAllCountriesWhenResponseCodeIs200() {
        fakeInterceptor.responseCode = 200
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriberViewObjectList)
        testSubscriberViewObjectList.assertValue { !it.isHasError }
    }

    @Test
    fun getCountryWhenResponseCodeIs200AssertValues() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsSuccess()
            .data
        //val expectedTestData = listOf(Country(CountriesDataProvider.providePoland()))
        fakeInterceptor.responseCode = 200
        remoteDataSource.getCountryByAlpha(alpha)
            .map { it.data }
            .subscribe(testSubscriberCountry)
        testSubscriberCountry.assertValues(expectedTestData)
    }

    @Test
    fun getCountryWhenResponseCodeIs404() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsError()
        fakeInterceptor.responseCode = 404
        remoteDataSource.getAllCountries()
            .map { it.isHasError }
            .subscribe(errorSubscriber)
        errorSubscriber.assertValues(expectedTestData.isHasError)
    }

    @Test
    fun getCountryWhenResponseCodeIs204() {
        fakeInterceptor.responseCode = 204
        remoteDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isHasError }
    }

    @Test
    fun getCountryWhenResponseCodeIs200() {
        fakeInterceptor.responseCode = 200
        remoteDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { !it.isHasError }
    }


}