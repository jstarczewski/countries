package com.clakestudio.pc.countries.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CountryDao {

    @Query("SELECT * FROM DbCountry")
    fun getAllCountries(): Single<List<DbCountry>>

    @Query("SELECT * FROM DbCountry WHERE alpha3Code = :alpha3Code")
    fun getCountryByAlpha3Code(alpha3Code: String) : Single<DbCountry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCountry(dbCountry : DbCountry)

}