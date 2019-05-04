package com.clakestudio.pc.countries.ui.countires

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.countries.data.remote.CountriesRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countriesRemoteDataSource: CountriesRemoteDataSource) : ViewModel() {

    val countries: ObservableArrayList<String> = ObservableArrayList()
    private val _countries = ArrayList<String>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun init() = compositeDisposable.add(
            countriesRemoteDataSource.getAllCountries()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it.forEach {
                            _countries.add(it.name)
                            addAll()
                        }
                    }
    )

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun filter(name: String) = if (name.isNotEmpty()) addOnlyThoseContainingPattern(name) else addAll()

    fun addAll() {
        countries.clear()
        countries.addAll(_countries)
    }

    fun addOnlyThoseContainingPattern(pattern: String) {
        countries.clear()
        countries.addAll(_countries.filter { it.contains(pattern) })
    }
}
