package com.clakestudio.pc.countries.data

import android.util.Log
import com.clakestudio.pc.countries.data.source.CountriesDataSource
import com.clakestudio.pc.countries.data.source.remote.RemoteDataUnavailableException
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.clakestudio.pc.countries.vo.Country
import com.clakestudio.pc.countries.vo.ViewObject
import io.reactivex.Flowable
import javax.inject.Inject

@OpenForTesting
class CountriesRepository @Inject constructor(
    private val countriesRemoteDataSource: CountriesDataSource,
    private val countriesLocalDataSource: CountriesDataSource
) : CountriesDataSource {

/*
    private var isUpToDate = false
    private var cachedCountries : ViewObject<List<Country>> = ViewObject.error("Data not loaded", null)
*/
    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
/*
        if (!cachedCountries.isHasError && isUpToDate) {
            Log.e("Data from in memory", "Data")
            Flowable.just(cachedCountries)
        } else*/
            countriesRemoteDataSource.getAllCountries()
                .doOnNext { countries ->
                    countries.data?.forEach {
                        countriesLocalDataSource.saveCountry(it)
                    }
                }
                .map {
                    if (it.isHasError)
                        throw RemoteDataUnavailableException(
                            it.errorMessage
                                ?: "Data fetch error"
                        )
                    it
                }.onErrorResumeNext(countriesLocalDataSource.getAllCountries())

                    /*
                .map {
                    if (isUpToDate && !it.isHasError)
                        cachedCountries = it
                    it
                }*/


    override fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>> =
        countriesRemoteDataSource.getCountryByAlpha(alpha).map {
            if (it.isHasError)
                throw RemoteDataUnavailableException(it.errorMessage ?: "Error occured")
            it
        }.onErrorResumeNext(countriesLocalDataSource.getCountryByAlpha(alpha))

    override fun saveCountry(country: Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*
    override fun getAllCountries(): Flowable<ViewObject<List<DbCountry>>> =
        Flowable.concatArrayEagerDelayError(
            getAllCountriesFromLocalDataSource(),
            getAllCountriesFromRemoteDataSource()
                .materialize()
                .map { notification ->
                    if (notification.isOnError)
                        ViewObject.error(notification.error!!.localizedMessage, null)
                    notification
                }
               /* .filter {
                    !it.isOnError
                }*/
                .dematerialize { it }
                .debounce(500, TimeUnit.MILLISECONDS)
        )
        */
}