package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.util.FakeInterceptor
import com.clakestudio.pc.countries.util.RetrofitWithFakeInterceptorInjection
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CountriesRemoteDataSourceHandleResponseFunTest {

    private val interceptor = FakeInterceptor()
    private val countriesRestAdapter =
        FakeCountriesRestAdapter(RetrofitWithFakeInterceptorInjection.provideRetrofitWithoutRxAdapterFactory(interceptor))
    private val remoteDataSource = SyncRemoteDataSource(countriesRestAdapter)

    @Test
    fun handleResponseSuccess200() {
        interceptor.responseCode = 200
        val viewObject = remoteDataSource.handleResponse(countriesRestAdapter.getCountryByName("POL").execute())
        assertFalse(viewObject.isHasError)
    }

    @Test
    fun handleResponseError404() {
        interceptor.responseCode = 404
        val viewObject = remoteDataSource.handleResponse(countriesRestAdapter.getCountryByName("POL").execute())
        assertTrue(viewObject.isHasError)
    }

    @Test
    fun handleResponseError204() {
        interceptor.responseCode = 204
        val viewObject = remoteDataSource.handleResponse(countriesRestAdapter.getCountryByName("POL").execute())
        assertTrue(viewObject.isHasError)
    }

}
