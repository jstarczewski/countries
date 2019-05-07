package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.ui.details.Country
import com.clakestudio.pc.countries.util.SchedulersProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@OpenForTesting
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesDataSource,
    private val appSchedulers: SchedulersProvider
) :
    ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val countries: ObservableArrayList<String> = ObservableArrayList()
    private val _countries = MutableLiveData<List<Country>>()

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _navigationLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val navigationLiveEvent: LiveData<String> = _navigationLiveEvent

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message : LiveData<String> = _message


    fun load() {
        if (_countries.value.isNullOrEmpty()) {
            init()
        } else {
            _loading.value = false
        }
    }


    private fun init() {
        _loading.value = true
        compositeDisposable.add(
            countriesRepository.getAllCountries()
                .startWith(ViewObject.loading(null))
                .subscribeOn(appSchedulers.ioScheduler())
                .observeOn(appSchedulers.uiScheduler())
                .subscribe {
                    when {
                        it.isHasError -> {
                            _error.value = it.errorMessage + "\n Swipe to refresh"
                            _loading.value = false
                        }
                        it.isLoading -> {
                            _loading.value = true
                        }
                        else -> {
                            if (!it.isUpToDate!!)
                                _message.value = "Data is loaded from cache"
                            _countries.value = it.data?.sortedBy { it.countryName }
                            _loading.value = false
                            _error.value = ""
                            addAll()
                        }
                    }
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun filter(name: String) {
        if (name.length > 2) addOnlyThoseContainingPattern(name)
        if (name.isEmpty()) addAll()
    }

    private fun addAll() {
        countries.clear()
        _countries.value?.forEach {
            countries.add(it.countryName)
        }
    }

    private fun addOnlyThoseContainingPattern(pattern: String) {
        countries.clear()
        _countries.value?.forEach {
            if (it.countryName.toLowerCase().contains(pattern.toLowerCase()))
                countries.add(it.countryName)
        }
    }

    fun exposeNavigationDestinationCode(destinationName: String) {
        _navigationLiveEvent.value = _countries.value?.find { it.countryName == destinationName }?.alpha3Code ?: "POL"
    }
}
