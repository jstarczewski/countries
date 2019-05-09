package com.clakestudio.pc.countries.adapters.details

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object DetailsBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:details")
    fun setDetails(recyclerView: RecyclerView, details: ArrayList<Pair<String, String?>>) {
        with(recyclerView.adapter as DetailAdapter) {
            replaceData(details)
        }
    }
}