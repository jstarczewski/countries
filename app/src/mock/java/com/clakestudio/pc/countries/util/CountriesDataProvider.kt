package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.Country

object CountriesDataProvider {


    fun provideSampleCountries() {

    }

    fun provideColombia() =
        Country(
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
                Country.Currency(
                    "COP",
                    "Colombian peso",
                    "$"
                )
            ),
            listOf(
                Country.Language(
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
                Country.RegionalBlocs(
                    acronym = "PA",
                    name = "Pacific Alliance",
                    otherAcronyms = listOf(""),
                    otherNames = listOf("Alianza del Pacífico")
                ),
                Country.RegionalBlocs(
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
        Country(


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
                Country.Currency(
                    code = "PLN",
                    name = "Polish złoty",
                    symbol = "zł"
                )
            ),
            languages = listOf(
                Country.Language(
                    iso639_1 = "pl",
                    iso639_2 = "pol",
                    name = "Polish",
                    nativeName = "język polski",
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
                Country.RegionalBlocs(
                    acronym = " EU ",
                    name = " European Union",
                    otherAcronyms = listOf(),
                    otherNames = listOf()
                )
            ),
            cioc = "POL"
        )
}