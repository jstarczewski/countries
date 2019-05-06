package com.clakestudio.pc.countries.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CountryDao {

    @Query("SELECT * FROM Country")
    fun getAllCountries(): Single<List<Country>>

    @Query("SELECT * FROM Country WHERE alpha3Code = :alpha3Code")
    fun getCountryByAlpha3Code(alpha3Code: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCountry(country : com.clakestudio.pc.countries.ui.details.Country)

}