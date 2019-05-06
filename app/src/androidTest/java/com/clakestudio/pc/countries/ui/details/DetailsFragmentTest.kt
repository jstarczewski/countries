package com.clakestudio.pc.countries.ui.details

import org.junit.Assert.*

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DEL
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
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.data.CountriesRepository
import com.clakestudio.pc.countries.testing.SingleFragmentActivity


class DetailsFragmentTest {



    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    private val countriesFragment = TestDetailsFragment()

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var detailsRepository: CountriesRepository

    @Before
    fun setUp() {
        
    }


    class TestDetailsFragment : DetailsFragment() {
        val navController = mock<NavController>(NavController::class.java)
        override fun navController() = navController
    }

}