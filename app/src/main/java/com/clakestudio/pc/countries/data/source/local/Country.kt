package com.clakestudio.pc.countries.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    val alpha3Code : String,
    val countryName: String,
    val countryFlagUrl : String,
    val latlng: List<String>,
    val details : List<Pair<String, String?>>
)