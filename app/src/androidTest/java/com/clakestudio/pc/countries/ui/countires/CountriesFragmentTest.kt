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
import com.clakestudio.pc.countries.testing.SingleFragmentActivity
import com.clakestudio.pc.countries.util.ViewModelUtil
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

    private var countries = ObservableArrayList<String>()

    @Before
    fun setUp() {
        countriesViewModel = mock(CountriesViewModel::class.java)
        activityRule.activity.setFragment(countriesFragment)
        countriesFragment.viewModelFactory = ViewModelUtil.createFor(countriesViewModel)
        //`when`(countriesViewModel.load()).then { doNothing() }
        `when`(countriesViewModel.navigationLiveEvent).thenReturn(navigateLiveEvent)
        val navObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(navObserver)

        val errorObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(errorObserver)
        `when`(countriesViewModel.error).thenReturn(error)

        `when`(countriesViewModel.loading).thenReturn(loading)
        val loadingObserver = mock(Observer::class.java) as Observer<String>
        countriesViewModel.navigationLiveEvent.observeForever(loadingObserver)
        countries.add("Angola")
        `when`(countriesViewModel.countries).thenReturn(countries)
        navigateLiveEvent.value = "Angola"
    }

    @Test
    fun testItemInRecyclerClickedOpensOtherFragment() {
        //Espresso.onView(withText("Angola")).perform(click())
        //verify(countriesFragment.navController).navigate(R.id.action_countriesFragment_to_detailsFragment)
    }

    class TestCountriesFragment : CountriesFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

}