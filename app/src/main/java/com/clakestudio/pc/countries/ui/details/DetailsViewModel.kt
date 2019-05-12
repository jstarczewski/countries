package com.clakestudio.pc.countries.ui.details

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.util.SchedulersProvider
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val countryRepository: CountriesDataSource,
    private val appSchedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var alpha = String()

    val countryName: ObservableField<String> = ObservableField()
    val details: ObservableArrayList<Pair<String, String?>> = ObservableArrayList()
    val error: ObservableField<String> = countryName

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _latlng: MutableLiveData<Pair<Double?, Double?>> = MutableLiveData()
    val latlng: LiveData<Pair<Double?, Double?>> = _latlng

    private val _countryFlagUrl: MutableLiveData<String> = MutableLiveData()
    val countryFlagUrl: LiveData<String> = _countryFlagUrl

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    private var isUpToDate = false
    val message: LiveData<String> = _message

    fun load(alpha: String) {
        if (details.isEmpty() || alpha != this.alpha) {
            loadCountryDataByAlphaCode(alpha)
            this.alpha = alpha
        } else {
            _loading.value = false
        }
    }

    fun refresh() {
        if (!isUpToDate) loadCountryDataByAlphaCode(alpha)
        else
            _loading.value = false
    }

    private fun loadCountryDataByAlphaCode(alpha: String) = compositeDisposable.add(
        countryRepository.getCountryByAlpha(alpha)
            .startWith(ViewObject.loading(null))
            .subscribeOn(appSchedulersProvider.ioScheduler())
            .observeOn(appSchedulersProvider.uiScheduler())
            .subscribe({
                when {
                    it.isHasError -> {
                        error.set("${it.errorMessage}\nSwipe to refresh")
                        details.clear()
                        _loading.value = false
                    }
                    it.isLoading -> {
                        _loading.value = true
                    }
                    else -> {
                        _loading.value = false
                        handleData(it)
                    }
                }
            }, {
                error.set("Fatal error occurred, please try again later")
            })
    )

    private fun handleData(countryViewObject: ViewObject<Country>) {
        if (!countryViewObject.isUpToDate!!) {
            _message.value = "Data is loaded from cache"
            isUpToDate = false
        } else {
            isUpToDate = true
        }
        exposeData(countryViewObject.data!!)
    }

    /**
     * Data is exposed to user with databinding and liveData
     * */

    fun exposeData(country: Country) {
        countryName.set(country.countryName)
        _countryFlagUrl.value = country.countryFlagUrl
        _latlng.value = latLngStringToDouble(country.latlng)
        details.clear()
        details.addAll(country.countryDetails)
    }

    fun latLngStringToDouble(latLtnString: List<String?>) =
        if (!latLtnString.isNullOrEmpty()) Pair(
            latLtnString[0]?.toDouble(),
            latLtnString[1]?.toDouble()
        ) else null


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
