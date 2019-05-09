package com.clakestudio.pc.countries.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(CountriesTypeConverter::class)
data class DbCountry(
    @PrimaryKey
    val alpha3Code : String,
    val countryName: String,
    val countryFlagUrl : String,
    val latlng: String,
    val details : List<Pair<String, String?>>
)