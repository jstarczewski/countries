package com.clakestudio.pc.countries.ui.details

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _latlng: MutableLiveData<Pair<Double?, Double?>> = MutableLiveData()
    private val _countryFlagUrl: MutableLiveData<String> = MutableLiveData()

    val latlng: LiveData<Pair<Double?, Double?>> = _latlng
    val countryFlagUrl: LiveData<String> = _countryFlagUrl

    fun load(alpha: String) {
        if (details.isEmpty() || alpha != this.alpha) {
            loadCountryDataByAlphaCode(alpha)
        } else {
            _countryFlagUrl.value = _countryFlagUrl.value
            _loading.value = false
        }
    }

    private fun loadCountryDataByAlphaCode(alpha: String) = compositeDisposable.add(
        countryRepository.getCountryByAlpha(alpha)
            .startWith(ViewObject.loading(null))
            .subscribeOn(appSchedulersProvider.ioScheduler())
            .observeOn(appSchedulersProvider.uiScheduler())
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
                        details.clear()
                        _error.value = ""
                        _loading.value = false
                     //   loadData(it.data!!.find { it.alpha3Code == alpha }!!)
                        loadData(it.data!!)
                        this@DetailsViewModel.alpha = alpha
                    }
                }

            }
    )

    fun loadData(country: Country) {
        countryName.set(country.countryName)
        _countryFlagUrl.value = country.countryFlagUrl
        _latlng.value = latlngStringToDouble(country.latlng)
        details.addAll(country.countryDetails)
    }

    fun latlngStringToDouble(latltnString: List<String?>) =
        if (!latltnString.isNullOrEmpty()) Pair(latltnString[0]?.toDouble(), latltnString[1]?.toDouble()) else null


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
