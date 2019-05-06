package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.util.RetrofitWithFakeInterceptroInjection
import io.reactivex.Flowable
import junit.framework.Assert.assertFalse
import org.junit.Test

import org.junit.Before


class CountriesRemoteDataSourceTest {

    /*
    private val httpUrl = HttpUrl.Builder()
        .scheme("https")
       .host("example.com")
        .build()
    private val request = Request.Builder()
        .url(httpUrl)
        .build()

    private val okHttpResponseBuilder = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_1_1)
        .code(302)*/

    private val interceptor = FakeInterceptor()
    private val countriesRestAdapter =
        FakeCountriesRestAdapter(RetrofitWithFakeInterceptroInjection.provideRetrofit(interceptor))
    private val remotedataSource = SyncRemoteDataSource(countriesRestAdapter)

    @Before
    fun setUp() {

    }


    @Test
    fun handleResponse() {
        val viewObject = remotedataSource.handleResponse(countriesRestAdapter.getAllCountries())
        assertFalse(viewObject.isHasError)

    }
}