package com.clakestudio.pc.countries.data.source.local

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

    @Before
    fun setUp() {
        `when`(countryDao.getAllCountries()).thenReturn(Single.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()).map {
            it.data?.map {
                Country(
                    it.alpha3Code,
                    it.countryName,
                    it.countryFlagUrl,
                    it.latlng.joinToString(","),
                    it.countryDetails
                )
            }
        })
    }

    @Test
    fun getAllCountries() {
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
    fun getCountryByAlpha() {
    }

    @Test
    fun saveCountry() {
    }

    @Test
    fun getAllCountriesFromLocalDataSource() {
    }

    @Test
    fun getCountryByAlphaFromLocalDataSource() {
    }
}