package com.clakestudio.pc.countries.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clakestudio.pc.countries.data.FakeCountriesRepository
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTestErrorData {

    private val viewModel =
        DetailsViewModel(FakeCountriesRepository(true), TestSchedulersProvider())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel.load("POL")
    }

    @Test
    fun getError() {
        assertEquals(viewModel.error.get(), "Test error single country\nSwipe to refresh")
    }
}