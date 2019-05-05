package com.clakestudio.pc.countries.ui.details

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.countries.data.CountryRepository
import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val countryRepository: CountryRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val countryName: ObservableField<String> = ObservableField()
    val details: ObservableArrayList<Pair<String, String?>> = ObservableArrayList()

    private val _latlng: MutableLiveData<Pair<Double?, Double?>> = MutableLiveData()
    private val _countryFlagUrl: MutableLiveData<String> = MutableLiveData()

    val latlng: LiveData<Pair<Double?, Double?>> = _latlng
    val countryFlagUrl: LiveData<String> = _countryFlagUrl

    fun getDataByName(name: String) = compositeDisposable.add(
        countryRepository.getCountryByName(name)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .materialize()
            .map {
                if (it.isOnError) {

                }
                it
            }
            .dematerialize<ViewObject<com.clakestudio.pc.countries.data.Country>>()
            .subscribe {
                when {
                    it.isHasError -> {

                    }
                    it.isLoading -> {

                    }
                    else -> {
                        details.clear()
                        loadData(Country(it.data!!))
                    }
                }

            }
    )

    fun loadData(country: com.clakestudio.pc.countries.vo.Country) {
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
