package com.clakestudio.pc.countries.ui.details

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.vo.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val remoteDataSource: CountriesRemoteDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    val countryName : ObservableField<String> = ObservableField()
    val countryFlagUrl : ObservableField<String> = ObservableField()
    val details : ObservableArrayList<Pair<String, String?>> = ObservableArrayList()
    val latlng : ObservableArrayList<String> = ObservableArrayList()

    fun getDataByName() = remoteDataSource.getCountryByName("COL")
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("Det", it.toString())
                loadData(Country(it))
            }

    fun loadData(country : Country) {
        countryName.set(country.countryName)
        countryFlagUrl.set(country.countryFlagUrl)
        details.addAll(country.countryDetails)
        latlng.addAll(country.latlng)
    }

}
