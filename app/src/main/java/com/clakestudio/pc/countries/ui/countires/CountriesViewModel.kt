package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countriesRemoteDataSource: CountriesRemoteDataSource) :
    ViewModel() {

    val countries: ObservableArrayList<String> = ObservableArrayList()
    private val _countries = ArrayList<Country>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _destinationAlpha: MutableLiveData<String> = MutableLiveData()
    val navigateLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()

    fun init() = compositeDisposable.add(
        countriesRemoteDataSource.getAllCountries()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _countries.addAll(it)
                addAll()
            }

    )


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun filter(name: String) =
        if (name.isNotEmpty() && name.length > 2) addOnlyThoseContainingPattern(name) else addAll()

    fun addAll() {
        countries.clear()
        _countries.forEach {
            countries.add(it.name)
        }
    }

    fun addOnlyThoseContainingPattern(pattern: String) {
        countries.clear()
        _countries.forEach {
            if (it.name.toLowerCase().contains(pattern.toLowerCase()))
                countries.add(it.name)
        }
    }

    fun exposeNavigationDestinationCode(destinationName: String) {
        navigateLiveEvent.value = _countries.find { it.name == destinationName }?.alpha3Code
    }
}
