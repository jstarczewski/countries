package com.clakestudio.pc.countries.ui.details

import android.os.Bundle
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.clakestudio.pc.countries.util.AppSchedulersProvider
import com.clakestudio.pc.countries.util.CountriesDataProvider
import com.clakestudio.pc.countries.util.RecyclerViewMatcher
import com.clakestudio.pc.countries.util.ViewModelUtil
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.testing.SingleFragmentActivity
import java.util.concurrent.TimeUnit


class DetailsFragmentTest {


    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    private lateinit var detailsFragment: TestDetailsFragment

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var countriesRepository: CountriesRepository
    private var testObject = CountriesDataProvider.providePoland()


    @Before
    fun setUp() {
        detailsFragment = TestDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putString("alpha", "POL")
            //arguments?.putString("alpha", "POL")
            arguments = DetailsFragmentArgs.fromBundle(bundle).toBundle()
        }
        countriesRepository = mock(CountriesRepository::class.java)
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsSuccess()))
        detailsViewModel = DetailsViewModel(countriesRepository, AppSchedulersProvider())
        detailsFragment.viewModelFactory = ViewModelUtil.createFor(detailsViewModel)
        activityRule.activity.setFragment(detailsFragment)
    }

    @Test
    fun checkIfDataAboutPolandIsVisibleAfterLoadFromBundle() {
        onView(withId(R.id.text_view_name)).check(matches(withText(testObject.name)))
    }


    @Test
    fun checkIfErrorMessageWasDispalyedInCaseOfErrorData() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        onView(withId(R.id.text_view_name)).check(matches(withText("Test error single country \n Swipe to refresh")))
    }

    @Test
    fun testIfDataIsReloadedAfterOnSwipeRefresh() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        onView(withId(R.id.text_view_name)).check(matches(withText("Test error single country \n Swipe to refresh")))
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsSuccess()))
        Espresso.onView(withId(R.id.neasted_scroll_view)).perform(swipeDown())
        Espresso.onView(withId(R.id.text_view_name)).check(matches(withText("Poland")))
    }

    /**
     * Will not pass because of there is no idling resource implemented yet
     *
     * */


    /*
    @Test
    fun testIfDataIsReloadedAfterDelayWithOnSwipeRefresh() {
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsError()))
        onView(withId(R.id.text_view_name)).check(matches(withText("Test error single country \n Swipe to refresh")))
        `when`(countriesRepository.getCountryByAlpha("POL")).thenReturn(Flowable.just(CountriesDataProvider.providePolandWrappedAsSuccess()).delay(2, TimeUnit.SECONDS))
        Espresso.onView(withId(R.id.neasted_scroll_view)).perform(swipeDown())
        Espresso.onView(withId(R.id.text_view_name)).check(matches(withText("Poland")))
    }
*/


    @Test
    fun testRecyclerViewItemDisplayProperData() {
        onView(recyclerViewDetailsMatcher().atPositionOnView(0, R.id.text_view_detail_value)).check(
            matches(
                withText(
                    testObject.topLevelDomain.joinToString(separator = ",")
                )
            )
        )
    }

    /**
     * Other view assertions
     * */


    class TestDetailsFragment : DetailsFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

    private fun recyclerViewDetailsMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recycler_view_details)
    }

}