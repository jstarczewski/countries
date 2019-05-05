package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.util.SchedulersProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@OpenForTesting
class CountriesViewModel @Inject constructor(private val countriesRepository: CountriesDataSource,
                                             private val appSchedulers: SchedulersProvider) :
    ViewModel() {

    val countries: ObservableArrayList<String> = ObservableArrayList()
    private val _countries = ArrayList<Country>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _navigationLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading
    val navigationLiveEvent: LiveData<String> = _navigationLiveEvent

    fun load() {
        if (_countries.isEmpty())
            init()
        else {
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
                //     .onErrorReturn {
                //        it.localizedMessage
                //       ViewObject(false, true, listOf(), "Network error")
                //    }
                .materialize()
                .map {
                    if (it.isOnError) {
                        _error.value = (it.error?.localizedMessage + "\nSwipe to refresh")
                        _loading.value = false
                    }
                    it
                }
                .filter {
                    !it.isOnError
                }
                .dematerialize<ViewObject<List<Country>>>()
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
                            _countries.addAll(it.data!!)
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
        _navigationLiveEvent.value = _countries.find { it.name == destinationName }?.alpha3Code
    }
}
