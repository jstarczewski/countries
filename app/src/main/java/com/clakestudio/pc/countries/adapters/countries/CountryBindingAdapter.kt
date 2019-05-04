package com.clakestudio.pc.countries.adapters.countries

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CountryBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:countries")
    fun setCountries(recyclerView: RecyclerView, countries: ArrayList<String>) {
        with(recyclerView.adapter as CountryAdapter) {
            replaceData(countries)
        }
    }

}