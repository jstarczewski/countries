package com.clakestudio.pc.countries.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val remoteDataSource: CountriesRemoteDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    fun getDataByName() = remoteDataSource.getCountryByName("COL")
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.e("Data", it.toString())
            }

}
