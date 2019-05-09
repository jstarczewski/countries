package com.clakestudio.pc.countries.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.TestSchedulersProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DetailsViewModelLiveDataTests {

    private val countriesRepository: CountriesDataSource = mock(CountriesRepository::class.java)
    //private val viewModel = DetailsViewModel(FakeCountriesRepository(false), TestSchedulersProvider())
    private val viewModel = DetailsViewModel(countriesRepository, TestSchedulersProvider())
    private val loadingObserver: Observer<Boolean> = mock(Observer::class.java) as Observer<Boolean>
    private val messageObserver: Observer<String> = mock(Observer::class.java) as Observer<String>
    private val countryFlagObserver: Observer<String> = mock(Observer::class.java) as Observer<String>
    private val latlngObserver: Observer<Pair<Double?, Double?>> =
        mock(Observer::class.java) as Observer<Pair<Double?, Double?>>


    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsSuccess()))
        viewModel.loading.observeForever(loadingObserver)
        viewModel.message.observeForever(messageObserver)
        viewModel.countryFlagUrl.observeForever(countryFlagObserver)
        viewModel.latlng.observeForever(latlngObserver)
    }

    @Test
    fun testLoadingStateChangesWhenLoadingValues() {
        viewModel.loading.observeForever(loadingObserver)
        viewModel.load("POL")
        Mockito.verify(loadingObserver, Mockito.times(1)).onChanged(true)
    }

    @Test
    fun testLoadingStateChangesToFalseWhenDataAlreadyLoaded() {
        viewModel.load("POL")
        Mockito.verify(loadingObserver).onChanged(true)
        viewModel.load("POL")
        Mockito.verify(loadingObserver, Mockito.times(2)).onChanged(false)
    }

    @Test
    fun testMessageStateWhenDataIsUpToDate() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedPolandWrappedAsSuccess()))
        viewModel.load("POL")
        Mockito.verify(loadingObserver).onChanged(true)
    }

    @Test
    fun testMessageStateWhenDataIsOutdated() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.provideOutdatedPolandWrappedAsSuccess()))
        viewModel.load("POL")
        Mockito.verify(loadingObserver).onChanged(false)
    }

    @Test
    fun testLiveDataChangesWhenDataIsExposed() {
        val testObject = CountriesDataProvider.provideColombia()
        viewModel.exposeData(Country(testObject))
        Mockito.verify(countryFlagObserver).onChanged(testObject.flag)
        Mockito.verify(latlngObserver).onChanged(viewModel.latLngStringToDouble(testObject.latlng))
    }


}

