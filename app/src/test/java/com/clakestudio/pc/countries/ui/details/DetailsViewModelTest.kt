package com.clakestudio.pc.countries.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.FakeCountriesRepository
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

class DetailsViewModelTest {

    private val viewModel = DetailsViewModel(FakeCountriesRepository(false), TestSchedulersProvider())

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        viewModel.load("Pol")
    }

    @Test
    fun getCountryName() {
        assertEquals("Poland", viewModel.countryName.get())
    }

    @Test
    fun getDetails() {
        assertFalse(viewModel.details.isNullOrEmpty())
    }

    @Test
    fun getLatlng() {
        assertEquals(Pair(52.0, 20.0), viewModel.latlng.value)
    }

    @Test
    fun getCountryFlagUrl() {
        viewModel.load("Poland")
        assertEquals(viewModel.countryFlagUrl.value, "https://restcountries.eu/data/pol.svg")
    }


    @Test
    fun exposeDataChangesLiveDataValues() {
        viewModel.exposeData(Country(CountriesDataProvider.provideColombia()))
    }

    @Test
    fun loadData() {
        viewModel.load("Colombia")
        assertEquals(viewModel.countryName.get(), "Colombia")
    }


    @Test
    fun latlngStringToDoubleDoubleDoubleOutput() {
        val out = viewModel.latLngStringToDouble(listOf("12", "12"))
        assertEquals(Pair(12.0, 12.0), out)
    }

    @Test
    fun latlngStringToDubleDoubleNullOutput() {
        val out = viewModel.latLngStringToDouble(listOf("12", null))
        assertEquals(Pair(12.0, null), out)
    }

    @Test
    fun latlngStringToDoubleNullInputData() {
        val out = viewModel.latLngStringToDouble(listOf())
        assertEquals(null, out)
    }


}