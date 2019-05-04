package com.clakestudio.pc.countries.ui.details

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import com.clakestudio.pc.countries.vo.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val remoteDataSource: CountriesRemoteDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    val countryName: ObservableField<String> = ObservableField()
    val details: ObservableArrayList<Pair<String, String?>> = ObservableArrayList()

    private val _latlng: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    private val _countryFlagUrl: MutableLiveData<String> = MutableLiveData()

    val latlng: LiveData<Pair<Double, Double>> = _latlng
    val countryFlagUrl: LiveData<String> = _countryFlagUrl

    fun getDataByName(name : String) = remoteDataSource.getCountryByName(name)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loadData(Country(it))
            }

    fun loadData(country: Country) {
        countryName.set(country.countryName)
        _countryFlagUrl.value = country.countryFlagUrl
        _latlng.value = latlngStringToDouble(country.latlng)
        details.addAll(country.countryDetails)
    }

    fun latlngStringToDouble(latltnString: List<String>) = Pair(latltnString[0].toDouble(), latltnString[1].toDouble())

}
