package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CountriesRepositoryTest {

    private val countriesLocalDataSource = mock(CountriesLocalDataSource::class.java)
    private val countriesRemoteDataSource = mock(CountriesRemoteDataSource::class.java)
    private val countriesRepository = CountriesRepository(countriesRemoteDataSource, countriesLocalDataSource)
    private val testSubscriber = TestSubscriber<List<Country>>()

    @Before
    fun setUp() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
        `when`(countriesLocalDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
    }

    @Test
    fun getAllCountriesTestReturnValueNumber() {
     //   countriesRepository.getAllCountries()
         //   .subscribe(testSubscriber)

        //testSubscriber.assertValue(ViewObject.success(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess(), true))
  //      testSubscriber.assertValueCount(2)
    }

    @Test
    fun getAllCountriesTestObtainedValues() {
       // testSubscriber.awaitTerminalEvent()
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess().data
        countriesRepository.getAllCountries()
            .map {
                it.data
            }
            .subscribe(testSubscriber)
        testSubscriber.assertValues(expectedTestData)

    }

    @Test
    fun getCountryByAlpha() {
    }

    @Test
    fun saveCountry() {
    }
}