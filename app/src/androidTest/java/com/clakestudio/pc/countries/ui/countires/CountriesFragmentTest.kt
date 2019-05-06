package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.testing.SingleFragmentActivity
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.RecyclerViewMatcher
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

    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var countriesRepository: CountriesRepository

    @Before
    fun setUp() {
        countriesRepository = mock(CountriesRepository::class.java)
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrapedAsSucces()))
        countriesViewModel = CountriesViewModel(countriesRepository, AppSchedulersProvider())
        countriesFragment.viewModelFactory = ViewModelUtil.createFor(countriesViewModel)
        activityRule.activity.setFragment(countriesFragment)

    }

    @Test
    fun testItemInRecyclerClickedOpensOtherFragment() {
        val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment()
        action.alpha = "POL"
        Espresso.onView(withText("Poland")).perform(click())
        verify(countriesFragment.navController).navigate(action)
    }

    @Test
    fun testItemsWereFilteredAndPolandWasNotVisible() {
        Espresso.onView(RecyclerViewMatcher(R.id.recycler_view_countries).atPositionOnView(0, R.id.text_view_name)).check(matches(withText("Colombia")))
    }

    @Test
    fun testItemsWereFilteredThenFilteredBackAndPolandIsAgainDisplayed() {
    }

    class TestCountriesFragment : CountriesFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

}