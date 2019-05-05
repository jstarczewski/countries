package com.clakestudio.pc.countries.ui.countires

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clakestudio.pc.countries.data.FakeCountriesRepository
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class CountriesViewModelTestSuccessData {

    private val viewModel = CountriesViewModel(FakeCountriesRepository(false),TestSchedulersProvider)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val patternPol = "pol"
    private val patternEmpty = "can"
    private val patternTooShort = "po"
    private val patternCol = "umbia"
    private val alpha3PolandCode = "POL"

    @Before
    fun setUp() {
        viewModel.load()
    }

    @Test
    fun getCountries() {
        assertEquals(viewModel.countries.size, 2)
    }

    @Test
    fun filterInputLongerThanTwoCharacters() {
        viewModel.filter(patternPol)
        assertEquals(viewModel.countries.size, 1)
        assertEquals(viewModel.countries[0], "Poland")
    }

    @Test
    fun filterInputShorterThanTwoCharacters() {
        viewModel.filter(patternPol)
        viewModel.filter(patternTooShort)
        assertEquals(viewModel.countries.size, 1)
    }

    @Test
    fun filterInputEmpty() {
        viewModel.countries.clear()
        viewModel.filter("")
        assertEquals(viewModel.countries.size, 2)
    }

    @Test
    fun addAll() {
        assertEquals(viewModel.countries.size, 2)
    }

    @Test
    fun addOnlyThoseContainingPatternEmptySet() {
        viewModel.addOnlyThoseContainingPattern(patternEmpty)
        assertTrue(viewModel.countries.isEmpty())
    }

    @Test
    fun addOnlyThoseContainingPatternSuccess() {
        viewModel.addOnlyThoseContainingPattern(patternPol)
        assertTrue(viewModel.countries.isNotEmpty())
        viewModel.countries.forEach {
            assertTrue(it.toLowerCase().contains(patternPol))
        }
    }

    @Test
    fun exposeNavigationDestinationCode() {
        viewModel.exposeNavigationDestinationCode("Poland")
        assertEquals(viewModel.navigationLiveEvent.value, alpha3PolandCode)
    }

    @Test
    fun exposeNavigationDestinationCodeWrong() {
        viewModel.exposeNavigationDestinationCode("Russia")
        assertNotEquals(viewModel.navigationLiveEvent.value, alpha3PolandCode)
    }
}