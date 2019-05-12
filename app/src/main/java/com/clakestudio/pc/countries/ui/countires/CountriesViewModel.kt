package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.util.SchedulersProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@OpenForTesting
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesDataSource,
    private val appSchedulers: SchedulersProvider
) : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _countries = MutableLiveData<List<Country>>()

    val countries: ObservableArrayList<String> = ObservableArrayList()
    val error: ObservableField<String> = ObservableField()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _navigationLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val navigationLiveEvent: LiveData<String> = _navigationLiveEvent

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> = _message

    private var isUpToDate = false

    fun load() {
        if (_countries.value.isNullOrEmpty() || !isUpToDate) {
            init()
        } else {
            _loading.value = false
        }
    }


    private fun init() {
        compositeDisposable.add(
            countriesRepository.getAllCountries()
                .startWith(ViewObject.loading(null))
                .subscribeOn(appSchedulers.ioScheduler())
                .observeOn(appSchedulers.uiScheduler())
                .subscribe({
                    when {
                        it.isHasError -> {
                            error.set(it.errorMessage)
                            _loading.value = false
                        }
                        it.isLoading -> {
                            _loading.value = true
                        }
                        else -> {
                            if (!it.isUpToDate!!) {
                                _message.value = "Data is loaded from cache"
                                isUpToDate = false
                            } else
                                isUpToDate = true
                            _countries.value = it.data?.sortedBy { it.countryName }
                            _loading.value = false
                            error.set("")
                            addAll()
                        }
                    }
                }, {
                    error.set("Fatal error occurred, please try again later")
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun filter(name: String) {
        if (name.length > 2) addOnlyThoseContainingPattern(name)
        else addAll()
    }

    private fun addAll() {
        countries.clear()
        _countries.value?.forEach {
            countries.add(it.countryName)
        }
    }

    fun addOnlyThoseContainingPattern(pattern: String) {
        countries.clear()
        _countries.value?.forEach {
            if (it.countryName.toLowerCase().contains(pattern.toLowerCase()))
                countries.add(it.countryName)
        }
    }

    fun exposeNavigationDestinationCode(destinationName: String) {
        _navigationLiveEvent.value =
            _countries.value?.find { it.countryName == destinationName }?.alpha3Code ?: "POL"
    }
}
