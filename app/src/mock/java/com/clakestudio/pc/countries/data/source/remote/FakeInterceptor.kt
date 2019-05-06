package com.clakestudio.pc.countries.data.source.remote

import com.clakestudio.pc.countries.BuildConfig
import com.clakestudio.pc.countries.util.CountriesDataProvider
import okhttp3.*

class FakeInterceptor : Interceptor {

    var responseCode = 200
    lateinit var response: Response

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri()
            val query = uri.query
            val parsedQuery: List<String> = query.split("=")
            val responseString =
                if (parsedQuery[0].toLowerCase() == "alpha" && parsedQuery[1] == CountriesDataProvider.provideColombiaJSON())
                    CountriesDataProvider.provideColombiaJSON()
                else ""
            response = Response.Builder()
                .code(responseCode)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()

        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }


}