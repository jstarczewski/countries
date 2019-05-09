package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.BuildConfig
import com.clakestudio.pc.countries.data.source.remote.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*

class FakeInterceptor : Interceptor {

    var responseCode = 200
    lateinit var response: Response
    private val countryType = object : TypeToken<Collection<Country>>() {}.type

    val countries = """
[{"name":"Poland","topLevelDomain":[".pl"],"alpha2Code":"PL","alpha3Code":"POL","callingCodes":["48"],"capital":"Warsaw","altSpellings":["PL","Republic of Poland","Rzeczpospolita Polska"],"region":"Europe","subregion":"Eastern Europe","population":38437239,"latlng":[52.0,20.0],"demonym":"Polish","area":312679.0,"gini":34.1,"timezones":["UTC+01:00"],"borders":["BLR","CZE","DEU","LTU","RUS","SVK","UKR"],"nativeName":"Polska","numericCode":"616","currencies":[{"code":"PLN","name":"Polish złoty","symbol":"zł"}],"languages":[{"iso639_1":"pl","iso639_2":"pol","name":"Polish","nativeName":"język polski"}],"translations":{"de":"Polen","es":"Polonia","fr":"Pologne","ja":"ポーランド","it":"Polonia","br":"Polônia","pt":"Polónia","nl":"Polen","hr":"Poljska","fa":"لهستان"},"flag":"https://restcountries.eu/data/pol.svg","regionalBlocs":[{"acronym":"EU","name":"European Union","otherAcronyms":[],"otherNames":[]}],"cioc":"POL"},
{"name":"Colombia","topLevelDomain":[".co"],"alpha2Code":"CO","alpha3Code":"COL","callingCodes":["57"],"capital":"Bogotá","altSpellings":["CO","Republic of Colombia","República de Colombia"],"region":"Americas","subregion":"South America","population":48759958,"latlng":[4.0,-72.0],"demonym":"Colombian","area":1141748.0,"gini":55.9,"timezones":["UTC-05:00"],"borders":["BRA","ECU","PAN","PER","VEN"],"nativeName":"Colombia","numericCode":"170","currencies":[{"code":"COP","name":"Colombian peso","symbol":"${'$'}"}],"languages":[{"iso639_1":"es","iso639_2":"spa","name":"Spanish","nativeName":"Español"}],"translations":{"de":"Kolumbien","es":"Colombia","fr":"Colombie","ja":"コロンビア","it":"Colombia","br":"Colômbia","pt":"Colômbia","nl":"Colombia","hr":"Kolumbija","fa":"کلمبیا"},"flag":"https://restcountries.eu/data/col.svg","regionalBlocs":[{"acronym":"PA","name":"Pacific Alliance","otherAcronyms":[],"otherNames":["Alianza del Pacífico"]},{"acronym":"USAN","name":"Union of South American Nations","otherAcronyms":["UNASUR","UNASUL","UZAN"],"otherNames":["Unión de Naciones Suramericanas","União de Nações Sul-Americanas","Unie van Zuid-Amerikaanse Naties","South American Union"]}],"cioc":"COL"}]
"""

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