package com.clakestudio.pc.countries.data.source.local

import androidx.room.EmptyResultSetException
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CountriesLocalDataSourceTest {


    private val countryDao = mock(CountryDao::class.java)
    private val localDataSource = CountriesLocalDataSource(countryDao)
    private val testSubscriber = TestSubscriber<List<Country>>()
    private val testSubscribierViewObject = TestSubscriber<ViewObject<List<Country>>>()

    private val testSubscriberCountry = TestSubscriber<Country>()
    private val testSubscriberViewObjectCountry = TestSubscriber<ViewObject<Country>>()

    private val alpha = "POL"

    @Before
    fun setUp() {
        `when`(countryDao.getAllCountries()).thenReturn(Single.just(CountriesDataProvider.provideOutdatedSampleCountriesWrappedAsSuccess()).map { conuries ->
            conuries.data?.map {
                Country(
                    it.alpha3Code,
                    it.countryName,
                    it.countryFlagUrl,
                    it.latlng.joinToString(","),
                    it.countryDetails
                )
            }
        })
        `when`(countryDao.getCountryByAlpha3Code(alpha)).thenReturn(Single.just(CountriesDataProvider.provideOutdatedPolandWrappedAsSuccess()).map {
            Country(
                it.data!!.alpha3Code,
                it.data!!.countryName,
                it.data!!.countryFlagUrl,
                it.data!!.latlng.joinToString(","),
                it.data!!.countryDetails
            )
        })
    }

    @Test
    fun getAllCountriesWrappedAsSuccessAssertedAfterMappedToCertainValues() {
        val expectedTestData = CountriesDataProvider.provideCountries().map { Country(it) }
        localDataSource.getAllCountries()
            .map {
                it.data
            }
            .subscribe(testSubscriber)
        //testSubscriber.assertValues(null, expectedTestData)
        testSubscriber.assertValues(expectedTestData)
    }

    @Test
    fun getAllCountriesTestIfNoError() {
        val expectedData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        localDataSource.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isHasError == expectedData.isHasError }
    }

    @Test
    fun getAllCountriesWrappedAsSuccessCheckIfValuesOutdated() {
        //    val expectedData = CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()
        localDataSource.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isUpToDate == false }
    }

    @Test
    fun getAllCountriesWrappedAsErrorCheckIfIsError() {
        `when`(countryDao.getAllCountries())
            .thenReturn(Single.just(CountriesDataProvider.provideEmptyDataSetForLocalDataSourceTest()))
        assertTrue(CountriesDataProvider.provideEmptyDataSetForLocalDataSourceTest().isEmpty())
        localDataSource.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.isHasError }
    }


    @Test
    fun getAllCountriesWrappedAsErrorCheckErrorMessage() {
        val expectedMessage =
            "Cannot fetch data from network, no cache is available, check your connection and swipe to refresh"
        `when`(countryDao.getAllCountries())
            .thenReturn(Single.just(CountriesDataProvider.provideEmptyDataSetForLocalDataSourceTest()))
        localDataSource.getAllCountries()
            .subscribe(testSubscribierViewObject)
        testSubscribierViewObject.assertValue { it.errorMessage == expectedMessage }
    }

    @Test
    fun getCountryWrappedAsSuccessAssertedAfterMappedToCertainValues() {
        val expectedTestData = Country(CountriesDataProvider.providePoland())
        localDataSource.getCountryByAlpha(alpha)
            .map {
                it.data
            }
            .subscribe(testSubscriberCountry)
        testSubscriberCountry.assertValues(expectedTestData)
    }

    @Test
    fun getCountryTestIfNoError() {
        val expectedData = CountriesDataProvider.providePolandWrappedAsSuccess()
        localDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isHasError == expectedData.isHasError }
    }

    @Test
    fun getCountryWrappedAsSuccessCheckIfValuesOutdated() {
        localDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isUpToDate == false }
    }

    @Test
    fun getCountryWrappedAsErrorCheckIfIsError() {
        `when`(countryDao.getCountryByAlpha3Code(alpha))
            .thenReturn(
                Single.just(
                    Country(
                        "null",
                        "null",
                        "null",
                        "null",
                        listOf()
                    )
                ).doOnError { throw EmptyResultSetException("Test error") })
        //assertTrue(CountriesDataProvider.provideEmptyDataSetForLocalDataSourceTest().isEmpty())
        localDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.isHasError }
    }


    @Test
    fun getCountryWrappedAsErrorCheckErrorMessage() {
        val expectedMessage =
            "Cannot fetch data from network, no cache is available, check your connection and swipe to refresh"
        `when`(countryDao.getCountryByAlpha3Code(alpha))
            .thenReturn(
                Single.just(
                    Country(
                        "null",
                        "null",
                        "null",
                        "null",
                        listOf()
                    )
                ).doOnError { throw EmptyResultSetException("Test error") })
        localDataSource.getCountryByAlpha(alpha)
            .subscribe(testSubscriberViewObjectCountry)
        testSubscriberViewObjectCountry.assertValue { it.errorMessage == expectedMessage }
    }

}