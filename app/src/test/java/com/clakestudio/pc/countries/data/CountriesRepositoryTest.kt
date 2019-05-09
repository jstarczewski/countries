package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import com.clakestudio.pc.countries.vo.ViewObject
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
    private val testSubscriber = TestSubscriber<ViewObject<List<Country>>>()
    private val testSubscribierViewObject =
        TestSubscriber<ViewObject<List<Country>>>()

    @Before
    fun setUp() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
        `when`(countriesLocalDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedSampleCountriesWrappedAsSuccess()))
    }

    @Test
    fun getAllCountriesTestReturnValueNumber() {
        ViewObject.success(
            CountriesDataProvider.provideSampleCountriesWrappedAsSuccess(),
            true
        )
        countriesRepository.getAllCountries()
            .subscribe(testSubscriber)
        testSubscriber.assertValueCount(1)
    }

    @Test
    fun getAllCountriesTestObtainedValueData() {
        // testSubscriber.awaitTerminalEvent()
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        countriesRepository.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.data == expectedTestData.data }
    }

    @Test
    fun getAllCountriesTestObtainedDataIsUpToDate() {
        // testSubscriber.awaitTerminalEvent()
        //val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        countriesRepository.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isUpToDate == true }
    }

    @Test
    fun getAllCountriesTestObtainedDataHasNoError() {
        // testSubscriber.awaitTerminalEvent()
        //val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        countriesRepository.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { !it.isHasError }
    }

    @Test
    fun getAllCountriesWithErrorCheckIfIsNotUpToDate() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        countriesRepository.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isUpToDate == false }
    }

    @Test
    fun getAllCountriesWithErrorFromBothDataSourcesCheckIfError() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        `when`(countriesLocalDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        countriesRepository.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isHasError }
    }
}