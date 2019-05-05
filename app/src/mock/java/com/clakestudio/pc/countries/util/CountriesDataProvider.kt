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
}
