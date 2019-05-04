package com.clakestudio.pc.countries.ui.countires

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countriesRemoteDataSource: CountriesRemoteDataSource) : ViewModel() {
    // TODO: Implement the ViewModel


    fun init() {
        countriesRemoteDataSource.getAllCountries()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("List", it.toString())
            }
    }

}
