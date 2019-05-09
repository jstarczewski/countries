package com.clakestudio.pc.countries.ui.details

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.countries.SingleLiveEvent
import com.clakestudio.pc.countries.data.Country
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
    val message: LiveData<String> = _message

    fun load(alpha: String) {
        if (details.isEmpty() || alpha != this.alpha) {
            loadCountryDataByAlphaCode(alpha)
            this.alpha = alpha
        } else {
            //     _countryFlagUrl.value = _countryFlagUrl.value
            _loading.value = false
        }
    }

    fun refresh() {
        if (alpha.isNotEmpty()) loadCountryDataByAlphaCode(alpha)
    }

    fun loadCountryDataByAlphaCode(alpha: String) = compositeDisposable.add(
        countryRepository.getCountryByAlpha(alpha)
            .startWith(ViewObject.loading(null))
            .subscribeOn(appSchedulersProvider.ioScheduler())
            .observeOn(appSchedulersProvider.uiScheduler())
            .subscribe {
                when {
                    it.isHasError -> {
                        error.set("${it.errorMessage} \n Swipe to refresh")
                        details.clear()
                        _loading.value = false
                    }
                    it.isLoading -> {
                        _loading.value = true
                    }
                    else -> {
                        details.clear()
                        _loading.value = false
                        if (!it.isUpToDate!!)
                            _message.value = "Data is loaded from cache"
                        exposeData(it.data!!)
                    }
                }
            }
    )

    fun exposeData(country: Country) {
        countryName.set(country.countryName)
        _countryFlagUrl.value = country.countryFlagUrl
        _latlng.value = latLngStringToDouble(country.latLng)
        details.addAll(country.countryDetails)
    }

    fun latLngStringToDouble(latLtnString: List<String?>) =
        if (!latLtnString.isNullOrEmpty()) Pair(latLtnString[0]?.toDouble(), latLtnString[1]?.toDouble()) else null


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
