package com.clakestudio.pc.countries.data.source.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
object CountriesTypeConverter {

    @TypeConverter
    @JvmStatic
    fun arrayListOfPairsOfStringToString(data : ArrayList<Pair<String, String?>>) : String {
        return data.map {
            pair -> pair.first+"|"+pair.second
        }.joinToString(separator = "#")
    }

    @TypeConverter
    @JvmStatic
    fun stringContaininPairsOfStringToArrayListOfPairsOfString(data : String) : ArrayList<Pair<String, String?>> {
        return data.split("#").flatMap {
            pairString -> pairString.split("|").zipWithNext()
        } as ArrayList<Pair<String, String?>>
    }

}