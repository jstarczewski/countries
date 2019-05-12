package com.clakestudio.pc.countries.data

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

    /**
     * Repository is trying to fetch data from network, when it fails then
     * data from local db is loaded. When there is no data in db, suitable callback
     * message is passed within ViewObject. Data from db is passed with upToDate = false
     * information
     * */

    /**
     * After fetch from network data is saved to local db
     * */

    override fun getAllCountries(): Flowable<ViewObject<List<Country>>> =
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


    override fun getCountryByAlpha(alpha: String): Flowable<ViewObject<Country>> =
        countriesRemoteDataSource.getCountryByAlpha(alpha).map {
            if (it.isHasError)
                throw RemoteDataUnavailableException(it.errorMessage ?: "Error occured")
            it
        }.onErrorResumeNext(countriesLocalDataSource.getCountryByAlpha(alpha))

    /**
     *
     * Not implemented and unused, created only for purpose of having a single interface
     * for data sources
     * */

    override fun saveCountry(country: Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Old way of providing data via repository. I decided to populate data from remote data source
     * always when it is available, not when it is fast enough.
     * */

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