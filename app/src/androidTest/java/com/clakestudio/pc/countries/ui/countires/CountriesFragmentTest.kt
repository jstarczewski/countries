package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.testing.SingleFragmentActivity
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.ViewModelUtil
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CountriesFragmentTest {


    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    private val countriesFragment = TestCountriesFragment()
    private val navigateLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()
    private val error = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var countriesRepository: CountriesRepository

    private var countries = ObservableArrayList<String>()

    @Before
    fun setUp() {
        countriesRepository = mock(CountriesRepository::class.java)
        countriesViewModel = CountriesViewModel(countriesRepository, AppSchedulersProvider())
        activityRule.activity.setFragment(countriesFragment)
        countriesFragment.viewModelFactory = ViewModelUtil.createFor(countriesViewModel)
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrapedAsSucces()))

        //`when`(countriesViewModel.load()).then { doNothing() }

/*
        `when`(countriesViewModel.navigationLiveEvent).thenReturn(navigateLiveEvent)
        `when`(countriesViewModel.error).thenReturn(error)
        `when`(countriesViewModel.loading).thenReturn(loading)

        val navObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(navObserver)

        val errorObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(errorObserver)

        val loadingObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(loadingObserver)
        countries.add("Angola")
        `when`(countriesViewModel.countries).thenReturn(countries)
        navigateLiveEvent.value = "Angola"*/
    }

    @Test
    fun testItemInRecyclerClickedOpensOtherFragment() {
        val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment()
        action.alpha = "POL"
        Espresso.onView(withText("Poland")).perform(click())
        verify(countriesFragment.navController).navigate(action)
    }

    class TestCountriesFragment : CountriesFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

}