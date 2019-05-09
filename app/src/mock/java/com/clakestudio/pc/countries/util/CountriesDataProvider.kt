package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.data.source.remote.ApiCountry
import com.clakestudio.pc.countries.vo.ViewObject

object CountriesDataProvider {

    fun provideSampleCountriesWrappedAsSuccess(): ViewObject<List<Country>> {
        return ViewObject.success(listOf(provideColombia(), providePoland()).map {
            Country(it)
        }, true)
    }

    fun provideSampleCountriesWrappedAsError(): ViewObject<List<Country>> {
        return ViewObject.error("Test error", listOf(provideColombia(), providePoland()).map {
            Country(it)
        })
    }

    fun provideListWithSingleCountryWrappedAsSuccess(): ViewObject<List<Country>> {
        return ViewObject.success(listOf(providePoland()).map {
            Country(it)
        }, true)
    }

    fun provideListWithSingleCountryWrappedAsError(): ViewObject<List<Country>> {
        return ViewObject.error("Test error", listOf(providePoland()).map {
            Country(it)
        })
    }

    fun provideOutdatedSampleCountriesWrappedAsSuccess(): ViewObject<List<Country>> {
        return ViewObject.success(listOf(provideColombia(), providePoland()).map {
            Country(it)
        }, false)
    }

    fun provideEmptyDataSetForLocalDataSourceTest() =
        emptyList<com.clakestudio.pc.countries.data.source.local.DbCountry>()


    fun providePolandWrappedAsSuccess(): ViewObject<Country> =
        ViewObject.success(Country(providePoland()), true)

    fun provideOutdatedPolandWrappedAsSuccess(): ViewObject<Country> =
        ViewObject.success(Country(providePoland()), false)

    fun providePolandWrappedAsError() = ViewObject.error("Test error single country", null)

    fun provideColombiaWrappedAsSuccess() = ViewObject.success(
            Country(
                    provideColombia()
            ), true
    )

    fun provideCountries() = listOf(provideColombia(), providePoland())

    fun provideColombia() =
        ApiCountry(
            "Colombia",
            listOf(".co"),
            "CO",
            "COL",
            listOf("57"),
            "Bogotá",
            listOf(
                "CO",
                "Republic of Colombia",
                "República de Colombia"
            ),
            "Americas",
            "South America",
            "48759958",
            listOf("4.0", "-72.0"),
            "Colombian",
            "1141748.0",
            "55.9",
            listOf("UTC-05:00"),
            listOf("BRA", "ECU", "PAN", "PER", "VEN"),
            "Colombia",
            "170",
            listOf(
                ApiCountry.Currency(
                    "COP",
                    "Colombian peso",
                    "$"
                )
            ),
            listOf(
                ApiCountry.Language(
                    iso639_1 = "es",
                    iso639_2 = "spa",
                    name = "Spanish",
                    nativeName = "Español"
                )
            ),
            mapOf(
                "de" to "Kolumbien",
                "es" to "Colombia",
                "fr" to "Colombie",
                "ja" to "コロンビア",
                "it" to "Colombia",
                "br" to "Colômbia",
                "pt" to "Colômbia"
            ),
            "https://restcountries.eu/data/col.svg",
            listOf(
                ApiCountry.RegionalBlocs(
                    acronym = "PA",
                    name = "Pacific Alliance",
                    otherAcronyms = listOf(""),
                    otherNames = listOf("Alianza del Pacífico")
                ),
                ApiCountry.RegionalBlocs(
                    acronym = "USAN",
                    name = "Union of South American Nations",
                    otherAcronyms = listOf("UNASUR", "UNASUL", "UZAN"),
                    otherNames = listOf(
                        "Unión de Naciones Suramericanas",
                        "União de Nações Sul-Americanas",
                        "Unie van Zuid-Amerikaanse Naties",
                        "South American Union"
                    )
                )
            ),
            "COL"
        )

    fun providePoland() =
        ApiCountry(
            name = "Poland",
            topLevelDomain = listOf(".pl"),
            alpha2Code = "PL",
            alpha3Code = "POL",
            callingCodes = listOf("48"),
            capital = "Warsaw",
            altSpellings = listOf(
                "PL",
                "Republic of Poland",
                "Rzeczpospolita Polska"
            ),
            region = "Europe",
            subregion = "Eastern Europe",
            population = "38437239",
            latlng = listOf("52.0", "20.0"),
            demonym = "Polish",
            area = "312679.0",
            gini = "34.1",
            timezones = listOf("UTC+01=00"),
            borders = listOf(
                "BLR",
                "CZE",
                "DEU",
                "LTU",
                "RUS",
                "SVK",
                "UKR"
            ),
            nativeName = "Polska",
            numericCode = "616",
            currencies = listOf(
                ApiCountry.Currency(
                    code = "PLN",
                    name = "Polish złoty",
                    symbol = "zł"
                )
            ),
            languages = listOf(
                ApiCountry.Language(
                    iso639_1 = "pl",
                    iso639_2 = "pol",
                    name = "Polish",
                    nativeName = "język polski"
                )
            ),
            translations = mapOf(
                "de" to "Polen",
                "es" to "Polonia",
                "fr" to "Pologne",
                "ja" to "ポーランド",
                "it" to "Polonia",
                "br" to "Polônia",
                "pt" to "Polónia",
                "nl" to "Polen",
                "hr" to "Poljska",
                "fa" to "هستان"
            ),
            flag = "https://restcountries.eu/data/pol.svg",
            regionalBlocs = listOf(
                ApiCountry.RegionalBlocs(
                    acronym = " EU ",
                    name = " European Union",
                    otherAcronyms = listOf(),
                    otherNames = listOf()
                )
            ),
            cioc = "POL"
        )
}

const val colombia = """
[{
    "name": "Colombia",
    "topLevelDomain": [".co"],
    "alpha2Code": "CO",
    "alpha3Code": "COL",
    "callingCodes": ["57"],
    "capital": "Bogotá",
    "altSpellings": ["CO", "Republic of Colombia", "República de Colombia"],
    "region": "Americas",
    "subregion": "South America",
    "population": 48759958,
    "latlng": [4.0, -72.0],
    "demonym": "Colombian",
    "area": 1141748.0,
    "gini": 55.9,
    "timezones": ["UTC-05:00"],
    "borders": ["BRA", "ECU", "PAN", "PER", "VEN"],
    "nativeName": "Colombia",
    "numericCode": "170",
    "currencies": [{
        "code": "COP",
        "name": "Colombian peso",
        "symbol": "${'$'}"
    }],
    "languages": [{
        "iso639_1": "es",
        "iso639_2": "spa",
        "name": "Spanish",
        "nativeName": "Español"
    }],
    "translations": {
        "de": "Kolumbien",
        "es": "Colombia",
        "fr": "Colombie",
        "ja": "コロンビア",
        "it": "Colombia",
        "br": "Colômbia",
        "pt": "Colômbia"
    },
    "flag": "https://restcountries.eu/data/col.svg",
    "regionalBlocs": [{
        "acronym": "PA",
        "name": "Pacific Alliance",
        "otherAcronyms": [],
        "otherNames": ["Alianza del Pacífico"]
    }, {
        "acronym": "USAN",
        "name": "Union of South American Nations",
        "otherAcronyms": ["UNASUR", "UNASUL", "UZAN"],
        "otherNames": ["Unión de Naciones Suramericanas", "União de Nações Sul-Americanas", "Unie van Zuid-Amerikaanse Naties", "South American Union"]
    }],
    "cioc": "COL"
}]
"""