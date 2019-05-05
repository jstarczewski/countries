package com.clakestudio.pc.countries.util

import com.clakestudio.pc.countries.data.Country
import com.google.gson.Gson

object CountriesDataProvider {

    private val gson: Gson = Gson()

    fun provideSampleCountries() {

    }
    fun provideColombia() =
            gson.fromJson("[{\n" +
                    "    \"name\": \"Colombia\",\n" +
                    "    \"topLevelDomain\": [\".co\"],\n" +
                    "    \"alpha2Code\": \"CO\",\n" +
                    "    \"alpha3Code\": \"COL\",\n" +
                    "    \"callingCodes\": [\"57\"],\n" +
                    "    \"capital\": \"Bogotá\",\n" +
                    "    \"altSpellings\": [\"CO\", \"Republic of Colombia\", \"República de Colombia\"],\n" +
                    "    \"region\": \"Americas\",\n" +
                    "    \"subregion\": \"South America\",\n" +
                    "    \"population\": 48759958,\n" +
                    "    \"latlng\": [4.0, -72.0],\n" +
                    "    \"demonym\": \"Colombian\",\n" +
                    "    \"area\": 1141748.0,\n" +
                    "    \"gini\": 55.9,\n" +
                    "    \"timezones\": [\"UTC-05:00\"],\n" +
                    "    \"borders\": [\"BRA\", \"ECU\", \"PAN\", \"PER\", \"VEN\"],\n" +
                    "    \"nativeName\": \"Colombia\",\n" +
                    "    \"numericCode\": \"170\",\n" +
                    "    \"currencies\": [{\n" +
                    "        \"code\": \"COP\",\n" +
                    "        \"name\": \"Colombian peso\",\n" +
                    "        \"symbol\": \"\$\"\n" +
                    "    }],\n" +
                    "    \"languages\": [{\n" +
                    "        \"iso639_1\": \"es\",\n" +
                    "        \"iso639_2\": \"spa\",\n" +
                    "        \"name\": \"Spanish\",\n" +
                    "        \"nativeName\": \"Español\"\n" +
                    "    }],\n" +
                    "    \"translations\": {\n" +
                    "        \"de\": \"Kolumbien\",\n" +
                    "        \"es\": \"Colombia\",\n" +
                    "        \"fr\": \"Colombie\",\n" +
                    "        \"ja\": \"コロンビア\",\n" +
                    "        \"it\": \"Colombia\",\n" +
                    "        \"br\": \"Colômbia\",\n" +
                    "        \"pt\": \"Colômbia\"\n" +
                    "    },\n" +
                    "    \"flag\": \"https://restcountries.eu/data/col.svg\",\n" +
                    "    \"regionalBlocs\": [{\n" +
                    "        \"acronym\": \"PA\",\n" +
                    "        \"name\": \"Pacific Alliance\",\n" +
                    "        \"otherAcronyms\": [],\n" +
                    "        \"otherNames\": [\"Alianza del Pacífico\"]\n" +
                    "    }, {\n" +
                    "        \"acronym\": \"USAN\",\n" +
                    "        \"name\": \"Union of South American Nations\",\n" +
                    "        \"otherAcronyms\": [\"UNASUR\", \"UNASUL\", \"UZAN\"],\n" +
                    "        \"otherNames\": [\"Unión de Naciones Suramericanas\", \"União de Nações Sul-Americanas\", \"Unie van Zuid-Amerikaanse Naties\", \"South American Union\"]\n" +
                    "    }],\n" +
                    "    \"cioc\": \"COL\"\n" +
                    "}]", Country::class.java)

}