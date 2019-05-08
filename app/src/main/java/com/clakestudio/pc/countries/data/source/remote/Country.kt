package com.clakestudio.pc.countries.data.source.remote

import com.google.gson.annotations.SerializedName

data class Country(

    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("topLevelDomain")
    val topLevelDomain: List<String>,
    @field:SerializedName("alpha2Code")
    val alpha2Code: String,
    @field:SerializedName("alpha3Code")
    val alpha3Code: String,
    @field:SerializedName("callingCodes")
    val callingCodes: List<String>,
    @field:SerializedName("capital")
    val capital: String,
    @field:SerializedName("altSpellings")
    val altSpellings: List<String>,
    @field:SerializedName("region")
    val region: String,
    @field:SerializedName("subregion")
    val subregion: String,
    @field:SerializedName("population")
    val population: String,
    @field:SerializedName("latlng")
    val latlng: List<String>,
    @field:SerializedName("demonym")
    val demonym: String,
    @field:SerializedName("area")
    val area: String,
    @field:SerializedName("gini")
    val gini: String,
    @field:SerializedName("timezones")
    val timezones: List<String>,
    @field:SerializedName("borders")
    val borders: List<String>,
    @field:SerializedName("nativeName")
    val nativeName: String,
    @field:SerializedName("numericCode")
    val numericCode: String,
    @field:SerializedName("currencies")
    val currencies: List<Currency>,
    @field:SerializedName("languages")
    val languages: List<Language>,
    @field:SerializedName("translations")
    val translations: Map<String, String>,
    @field:SerializedName("flag")
    val flag: String,
    @field:SerializedName("regionalBlocs")
    val regionalBlocs: List<RegionalBlocs>,
    @field:SerializedName("cioc")
    val cioc: String

) {

    data class Currency(
        @field:SerializedName("code")
        val code: String,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("symbol")
        val symbol: String
    ) {
        override fun toString(): String {
            return "Code : $code \nName : $name \nSymbol : $symbol\n"
        }
    }

    data class Language(
        @field:SerializedName("iso639_1")
        val iso639_1: String,
        @field:SerializedName("iso639_2")
        val iso639_2: String,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("nativeName")
        val nativeName: String
    ) {
        override fun toString(): String {
            return "iso639_1 code : $iso639_1 \niso639_2 code : $iso639_2 \nName : $name \nNative name : $nativeName\n"
        }
    }

    data class RegionalBlocs(
        @field:SerializedName("acronym")
        val acronym: String,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("otherAcronyms")
        val otherAcronyms: List<String>,
        @field:SerializedName("otherNames")
        val otherNames: List<String>
    ) {
        override fun toString(): String {
            return "Name : $name \nAcronym :  $acronym \nOther Acronyms: ${otherAcronyms.joinToString()} \nOther names: ${otherNames.joinToString()}\n"
        }
    }

}

