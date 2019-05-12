package com.clakestudio.pc.countries.ui.countires

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import androidx.lifecycle.Observer
import com.clakestudio.pc.countries.util.CountriesDataProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CountriesViewModelLiveDataTests {

    private val countriesRepository: CountriesDataSource = mock(CountriesRepository::class.java)
    private val viewModel = CountriesViewModel(countriesRepository, TestSchedulersProvider())
    private val loadingObserver: Observer<Boolean> = mock(Observer::class.java) as Observer<Boolean>
    private val messageObserver: Observer<String> = mock(Observer::class.java) as Observer<String>
    private val navigationLiveEventObserver: Observer<String> = mock(Observer::class.java) as Observer<String>


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
        viewModel.loading.observeForever(loadingObserver)
        viewModel.message.observeForever(messageObserver)
        viewModel.navigationLiveEvent.observeForever(navigationLiveEventObserver)
    }

    @Test
    fun testLoadingStateChangesWhenLoadingValues() {
        viewModel.loading.observeForever(loadingObserver)
        viewModel.init()
        Mockito.verify(loadingObserver, Mockito.times(1)).onChanged(true)
    }

    @Test
    fun testLoadingStateChangesToFalseWhenDataAlreadyLoaded() {
        viewModel.init()
        Mockito.verify(loadingObserver).onChanged(true)
        viewModel.init()
        Mockito.verify(loadingObserver, Mockito.times(2)).onChanged(false)
    }

    @Test
    fun testMessageStateWhenDataIsUpToDate() {
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedSampleCountriesWrappedAsSuccess()))
        viewModel.init()
        Mockito.verify(loadingObserver).onChanged(true)
    }

    @Test
    fun testMessageStateWhenDataIsOutdated() {
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedSampleCountriesWrappedAsSuccess()))
        viewModel.init()
        Mockito.verify(loadingObserver).onChanged(false)
    }


    @Test
    fun testNavigationLiveEventStateAfterExposeNavigationDestinationCall() {
        viewModel.exposeNavigationDestinationCode("Poland")
        Mockito.verify(navigationLiveEventObserver).onChanged("POL")
    }

}