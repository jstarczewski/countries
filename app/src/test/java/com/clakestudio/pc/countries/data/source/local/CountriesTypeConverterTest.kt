package com.clakestudio.pc.countries.data.source.local

import com.clakestudio.pc.countries.data.Country
import com.clakestudio.pc.countries.util.CountriesDataProvider
import org.junit.Test

import org.junit.Assert.*

class CountriesTypeConverterTest {

    @Test
    fun arrayListOfPairsOfStringToStringAndThenRollbackToPreviousFormPoland() {
        val data = CountriesTypeConverter.arrayListOfPairsOfStringToString(Country(CountriesDataProvider.providePoland()).countryDetails)
        val rollback = CountriesTypeConverter.stringContaininPairsOfStringToArrayListOfPairsOfString(data)
        assertEquals(Country(CountriesDataProvider.providePoland()).countryDetails, rollback)
    }

    @Test
    fun arrayListOfPairsOfStringToStringAndThenRollbackToPreviousFormColombia()  {
        val data = CountriesTypeConverter.arrayListOfPairsOfStringToString(Country(CountriesDataProvider.provideColombia()).countryDetails)
        val rollback = CountriesTypeConverter.stringContaininPairsOfStringToArrayListOfPairsOfString(data)
        assertEquals(Country(CountriesDataProvider.provideColombia()).countryDetails, rollback)
    }
}