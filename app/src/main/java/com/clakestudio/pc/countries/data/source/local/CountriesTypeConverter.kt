package com.clakestudio.pc.countries.data.source.local

import androidx.room.TypeConverter

object CountriesTypeConverter {

    @TypeConverter
    @JvmStatic
    fun arrayListOfPairsOfStringToString(data : List<Pair<String, String?>>) : String {
        return data.map {
            pair -> pair.first+"|"+pair.second
        }.joinToString(separator = "#")
    }

    @TypeConverter
    @JvmStatic
    fun stringContaininPairsOfStringToArrayListOfPairsOfString(data : String) : List<Pair<String, String?>> {
        return data.split("#").flatMap {
            pairString -> pairString.split("|").zipWithNext()
        }
    }

    /*
    @TypeConverter
    @JvmStatic
    fun fromListToString(data: List<String>) = data.joinToString(",")

    @TypeConverter
    @JvmStatic
    fun fromStringToList(data: String) = data.toList()
*/
}