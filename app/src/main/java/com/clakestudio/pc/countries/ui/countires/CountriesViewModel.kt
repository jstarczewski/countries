package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.vo.Country
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

    /**
     * Most of functions are public because are tested
     * */

    /**
     * Init -> when fragment is created and displayed
     * We do not want to trigger data update when screen is rotated
     * init() load data once when there is no data
     * */


    fun init() {
        if (_countries.value.isNullOrEmpty()) {
            loadData()
        } else {
            _loading.value = false
        }
    }

    /**
     * Triggered by onSwipeRefresh -> user want to refresh data
     * */

    fun refresh() {
        if (!isUpToDate)
            loadData()
        else
            _loading.value = false
    }

    /**
     * Data loaded from repository and handled in viewModel based on callback that is
     * provided with data in ViewObject
     *
     *
     *  TO-DO
     *        Rate limiter -> because right now data when data is upUpToData it
     *        is still loaded once per app launch
     * */

    private fun loadData() {
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
                            error.set("")
                            _loading.value = false
                            handleData(it)
                        }
                    }
                }, {
                    error.set("Fatal error occurred, please try again later")
                })
        )
    }

    /**
     * User is informed whether loaded data is upToDate or not
     * */

    private fun handleData(countriesViewObject: ViewObject<List<Country>>) {
        if (!countriesViewObject.isUpToDate!!) {
            _message.value = "Data is loaded from cache"
            isUpToDate = false
        } else
            isUpToDate = true
        _countries.value = countriesViewObject.data?.sortedBy { it.countryName }
        addAll()
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
