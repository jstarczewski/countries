package com.clakestudio.pc.countries.ui.countires

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DEL
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.testing.SingleFragmentActivity
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.RecyclerViewMatcher
import com.clakestudio.pc.countries.util.ViewModelUtil
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import android.widget.EditText
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.util.AppSchedulersProvider


class CountriesFragmentTest {

    /**
     * Fragment is tested whether it behaves correctly with real callback from viewModel with mocked datasource
     * */

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    private val countriesFragment = TestCountriesFragment()

    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var countriesRepository: CountriesRepository

    @Before
    fun setUp() {
        countriesRepository = mock(CountriesRepository::class.java)
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
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
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Pol"), pressKey(KeyEvent.KEYCODE_ENTER))
        Espresso.onView(
            recyclerViewCountriesMatcher().atPositionOnView(
                0,
                R.id.text_view_name
            )
        ).check(matches(withText("Poland")))
    }

    @Test
    fun testItemsWereFilteredThenFilteredBackAndPolandIsAgainDisplayed() {
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Pol"), pressKey(KeyEvent.KEYCODE_ENTER))
        Espresso.onView(
            recyclerViewCountriesMatcher().atPositionOnView(
                0,
                R.id.text_view_name
            )
        ).check(matches(withText("Poland")))
        onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KEYCODE_DEL))
            .perform(pressKey(KEYCODE_DEL))
            .perform(pressKey(KEYCODE_DEL))
        Espresso.onView(
            recyclerViewCountriesMatcher().atPositionOnView(
                0,
                R.id.text_view_name
            )
        ).check(matches(withText("Colombia")))
    }


    @Test
    fun textViewWithErrorMessageIsVisibleWhenDataLoadedContainsErrors() {
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        Espresso.onView(withId(R.id.text_view_error)).check(matches(withText("Test error")))
    }

    @Test
    fun textViewErrorMessageDisappearsAndDataIsShowedAfterSwipeRefresh() {
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsError()))
        Espresso.onView(withId(R.id.text_view_error)).check(matches(withText("Test error")))
        `when`(countriesRepository.getAllCountries()).thenReturn(Flowable.just(CountriesDataProvider.provideSampleCountriesWrappedAsSuccess()))
        Espresso.onView(withId(R.id.recycler_view_countries)).perform(swipeDown())
        Espresso.onView(
            recyclerViewCountriesMatcher().atPositionOnView(
                0,
                R.id.text_view_name
            )
        ).check(matches(withText("Colombia")))

    }

    class TestCountriesFragment : CountriesFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

    private fun recyclerViewCountriesMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recycler_view_countries)
    }

}