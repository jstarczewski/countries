package com.clakestudio.pc.countries.ui.countires

import com.clakestudio.pc.countries.data.FakeCountriesRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CountriesViewModelTest {

    private val viewModel = CountriesViewModel(FakeCountriesRepository())

    @Before
    fun setUp() {

    }

    @Test
    fun getCountries() {
        viewModel.load()
        assertEquals(viewModel.countries.size, 1)
    }

    @Test
    fun getError() {
    }

    @Test
    fun getLoading() {
    }

    @Test
    fun getNavigationLiveEvent() {
    }

    @Test
    fun load() {
    }

    @Test
    fun onCleared() {
    }

    @Test
    fun filter() {
    }

    @Test
    fun addAll() {
    }

    @Test
    fun addOnlyThoseContainingPattern() {
    }

    @Test
    fun exposeNavigationDestinationCode() {
    }
}