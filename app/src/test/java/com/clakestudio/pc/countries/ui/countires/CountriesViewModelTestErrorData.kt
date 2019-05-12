package com.clakestudio.pc.countries.ui.countires

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clakestudio.pc.countries.data.FakeCountriesRepository
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.Rule

class CountriesViewModelTestErrorData {

    private val viewModel = CountriesViewModel(FakeCountriesRepository(true), TestSchedulersProvider())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel.init()
    }

    @Test
    fun getError() {
       assertEquals(viewModel.error.get(), "Test error")
    }

}
