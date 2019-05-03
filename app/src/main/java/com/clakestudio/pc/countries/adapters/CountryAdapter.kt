package com.clakestudio.pc.countries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val countries: ArrayList<String>,
    private val onCountryClickedCallback: (Unit) -> (Unit)
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = com.clakestudio.pc.countries.databinding.CountryBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener { onCountryClickedCallback.invoke(Unit) }
        return CountryViewHolder(binding)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(private val binding: com.clakestudio.pc.countries.databinding.CountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.textViewName.text = text
        }

    }

    private fun setCountries(countries: ArrayList<String>) {
        this.countries.clear()
        this.countries.addAll(countries)
        notifyDataSetChanged()
    }

    fun replaceData(countries: ArrayList<String>) = setCountries(countries)
}