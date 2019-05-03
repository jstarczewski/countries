package com.clakestudio.pc.countries.adapters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clakestudio.pc.countries.databinding.DetailBinding

class DetailAdapter(
        private val details: ArrayList<Pair<String, String>>
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DetailBinding.inflate(inflater, parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int = details.size


    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(details[position].first, details[position].second)
    }

    class DetailViewHolder(private val binding: DetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, value: String) {
            binding.textViewDetailName.text = name
            binding.textViewDetailValue.text = value
        }
    }
}