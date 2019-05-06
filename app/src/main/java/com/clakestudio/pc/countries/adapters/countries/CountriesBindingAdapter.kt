package com.clakestudio.pc.countries.adapters.countries

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CountriesBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:countries")
    fun setCountries(recyclerView: RecyclerView, countries: ArrayList<String>) {
        with(recyclerView.adapter as CountriesAdapter) {
            replaceData(countries)
        }
    }

}