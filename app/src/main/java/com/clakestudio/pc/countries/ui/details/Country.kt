package com.clakestudio.pc.countries.ui.details

import com.clakestudio.pc.countries.data.source.remote.Country as RemoteCountry

data class Country(
    val countryName: String,
    val alpha3Code: String,
    val countryFlagUrl: String,
    val latlng: List<String>,
    var countryDetails: List<Pair<String, String?>>
) {

    constructor(country: RemoteCountry) : this(
        country.name,
        country.alpha3Code,
        country.flag,
        country.latlng,
        arrayListOf()
    ) {
        countryDetails = createListOfDetails(country)
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

    private fun mapToString(map: Map<String, String>): String {
        var result = ""
        map.forEach {
            result += "${it.key} = ${it.value}\n"
        }
        return result
    }

}
