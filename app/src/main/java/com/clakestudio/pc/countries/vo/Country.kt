package com.clakestudio.pc.countries.vo

import com.clakestudio.pc.countries.data.Country as RemoteCountry

data class Country(val country: RemoteCountry,
                   val countryName: String = country.name,
                   val countryFlagUrl: String = country.flag,
                   val latlng: List<String> = country.latlng,
                   val countryDetails: ArrayList<Pair<String, String?>> = ArrayList()
) {
    init {
        countryDetails.addAll(createListOfDetails(country))
    }

    private fun createListOfDetails(country: RemoteCountry): ArrayList<Pair<String, String?>> =
            arrayListOf(
                    Pair("Top level domains", country.topLevelDomain.joinToString(separator = "\n")),
                    Pair("Alpha 2 code", country.alpha2Code),
                    Pair("Alpha 3 code", country.alpha3Code),
                    Pair("Calling codes", country.callingCodes.joinToString()),
                    Pair("Capital", country.capital),
                    Pair("Alternative spellings", country.altSpellings.joinToString(separator = "\n")),
                    Pair("Region", country.region),
                    Pair("Subregion", country.subregion),
                    Pair("Population", country.population),
                    Pair("Latitude and longitude", country.latlng.joinToString()),
                    Pair("Demonym", country.demonym),
                    Pair("Area", country.area),
                    Pair("Gini", country.gini),
                    Pair("Timezones", country.timezones.joinToString()),
                    Pair("Borders", country.borders.joinToString()),
                    Pair("Native name", country.nativeName),
                    Pair("Numeric code", country.numericCode),
                    Pair("Currencies", country.currencies.joinToString(separator = "\n")),
                    Pair("Languages", country.languages.joinToString(separator = "\n")),
                    Pair("Translations", mapToString(country.translations)),
                    Pair("Regional blocs", country.regionalBlocs.joinToString(separator = "\n")),
                    Pair("Cioc", country.cioc)

            )

    private fun mapToString(map: Map<String, String>) : String {
        var result = ""
        map.forEach{
            result += "${it.key} = ${it.value}\n"
        }
        return result
    }

}