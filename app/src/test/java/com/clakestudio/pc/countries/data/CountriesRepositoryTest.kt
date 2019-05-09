package com.clakestudio.pc.countries.data

import com.clakestudio.pc.countries.data.source.local.CountriesLocalDataSource
import com.clakestudio.pc.countries.data.source.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
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
    private val testSubscriberViewObject = TestSubscriber<ViewObject<List<Country>>>()

    private val alpha = "POL"

    private val testSubscriberCountry = TestSubscriber<Country>()
    private val testSubscriberViewObjectCountry = TestSubscriber<ViewObject<Country>>()

    @Before
    fun setUp() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
        `when`(countriesLocalDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedSampleCountriesWrappedAsSuccess()))

        `when`(countriesRemoteDataSource.getCountryByAlpha(alpha)).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsSuccess()))
        `when`(countriesLocalDataSource.getCountryByAlpha(alpha)).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedPolandWrappedAsSuccess()))
    }

    @Test
    fun getAllCountriesTestReturnValueNumber() {
        ViewObject.success(
            CountriesDataProvider.provideSampleCountriesWrappedAsSuccess(),
            true
        )
        countriesRepository.getAllCountries()
            .subscribe(testSubscriberViewObject)
        testSubscriberViewObject.assertValueCount(1)
    }

    @Test
    fun getAllCountriesTestObtainedValueData() {
        val expectedTestData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
            .data
        countriesRepository.getAllCountries()
            .map { it.data }
            .subscribe(testSubscriber)
        testSubscriber.assertValues(expectedTestData)
    }

    @Test
    fun getAllCountriesTestObtainedDataIsUpToDate() {
        countriesRepository.getAllCountries()
            .subscribe(testSubscriberViewObject)
        testSubscriberViewObject.assertValue { it.isUpToDate == true }
    }

    @Test
    fun getAllCountriesTestObtainedDataHasNoError() {
        countriesRepository.getAllCountries()
            .subscribe(testSubscriberViewObject)
        testSubscriberViewObject.assertValue { !it.isHasError }
    }

    @Test
    fun getAllCountriesWithErrorCheckIfIsNotUpToDate() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        countriesRepository.getAllCountries()
            .subscribe(testSubscriberViewObject)
        testSubscriberViewObject.assertValue { it.isUpToDate == false }
    }

    @Test
    fun getAllCountriesWithErrorFromBothDataSourcesCheckIfError() {
        `when`(countriesRemoteDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        `when`(countriesLocalDataSource.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        countriesRepository.getAllCountries()
            .subscribe(testSubscriberViewObject)
        testSubscriberViewObject.assertValue { it.isHasError }
    }

    @Test
    fun getCountryTestReturnValueNumber() {
        countriesRepository.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValueCount(1)
    }

    @Test
    fun getCountryTestObtainedValueData() {
        val expectedTestData = CountriesDataProvider.providePolandWrappedAsSuccess()
            .data
        countriesRepository.getCountryByAlpha(alpha)
            .map { it.data }
            .subscribe(testSubscriberCountry)
        testSubscriberCountry.assertValues(expectedTestData)
    }

    @Test
    fun getCountryTestObtainedDataIsUpToDate() {
        countriesRepository.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isUpToDate == true }
    }

    @Test
    fun getCountryTestObtainedDataHasNoError() {
        countriesRepository.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { !it.isHasError }
    }

    @Test
    fun getCountryWithErrorCheckIfIsNotUpToDate() {
        `when`(countriesRemoteDataSource.getCountryByAlpha(alpha)).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        countriesRepository.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isUpToDate == false }
    }

    @Test
    fun getCountryWithErrorFromBothDataSourcesCheckIfError() {
        `when`(countriesRemoteDataSource.getCountryByAlpha(alpha)).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        `when`(countriesLocalDataSource.getCountryByAlpha(alpha)).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        countriesRepository.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isHasError }
    }
}