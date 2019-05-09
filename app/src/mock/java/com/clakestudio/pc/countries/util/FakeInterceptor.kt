package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.BuildConfig
import com.clakestudio.pc.countries.data.source.remote.ApiCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*

class FakeInterceptor(var responseCode : Int = 200) : Interceptor {

    lateinit var response: Response
    private val countryType = object : TypeToken<Collection<ApiCountry>>() {}.type

    override fun intercept(chain: Interceptor.Chain): Response {
        //val pol = gson.toJson(CountriesDataProvider.providePoland())
        //val col = gson.toJson(CountriesDataProvider.provideColombia())
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val responseString =
                when {
                    (uri.endsWith("all")) -> Gson().toJson(CountriesDataProvider.provideCountries())
                    (uri.endsWith("POL")) -> Gson().toJson(CountriesDataProvider.providePoland())
                    else -> ""
                }
            response = Response.Builder()
                .code(responseCode)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()

        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }


}