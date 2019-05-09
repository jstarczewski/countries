package com.clakestudio.pc.countries.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Country::class], version = 1,
    exportSchema = false
)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

}