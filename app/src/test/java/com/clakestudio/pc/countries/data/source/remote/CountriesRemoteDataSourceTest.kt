package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CountriesRemoteDataSourceTest {

    private val adapter = mock(CountriesRestAdapter::class.java)
    private val remoteDataSource = CountriesRemoteDataSource(adapter)
    private val testSubscriber = TestSubscriber<ViewObject<List<Country>>>()

    @Before
    fun setUp() {
        /*`when`(adapter.getAllCountries()).thenReturn(
            Single.just(
                ViewObject.success(
                    CountriesDataProvider.provideCountries(),
                    true
                )
            )
        )*/

    }

    @Test
    fun getAllCountries() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        remoteDataSource.getAllCountries()
            .subscribe(testSubscriber)
        testSubscriber.assertValues(expectedTestData)
    }

    @Test
    fun getCountryByAlpha() {
    }

    @Test
    fun getAllCountriesFromRemoteDataSource() {
    }

    @Test
    fun saveCountry() {
    }

    @Test
    fun getCountriesFromRemoteDataSourceAndMap() {
    }

    @Test
    fun getCountryByAlphaFromRemoteDataSource() {
    }
}