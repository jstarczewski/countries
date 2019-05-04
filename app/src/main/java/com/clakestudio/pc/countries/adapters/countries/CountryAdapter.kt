package com.clakestudio.pc.countries.adapters.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val onCountryClickedCallback: (String) -> (Unit)
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), OnCountryClickListener {

    override fun onCountryClicked(position: Int) {
        onCountryClickedCallback.invoke(countries[position])
    }

    private val onCountryClickListener: OnCountryClickListener = this
    private var countries: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = com.clakestudio.pc.countries.databinding.CountryBinding.inflate(inflater, parent, false)
        return CountryViewHolder(binding, onCountryClickListener)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    class CountryViewHolder(
        private val binding: com.clakestudio.pc.countries.databinding.CountryBinding,
        private val onCountryClickListener: OnCountryClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onCountryClickListener.onCountryClicked(adapterPosition)
        }

        fun bind(text: String) {
            binding.textViewName.text = text
        }

    }

    private fun setCountries(countries: ArrayList<String>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    fun replaceData(countries: ArrayList<String>) {
        setCountries(countries)
    }
}