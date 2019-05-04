package com.clakestudio.pc.countries.ui.countires

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.adapters.countries.CountryAdapter
import com.clakestudio.pc.countries.databinding.CountriesFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import javax.inject.Inject


class CountriesFragment : Fragment(), Injectable, TextViewBindingAdapter.OnTextChanged {



    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: CountriesFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = CountriesFragmentBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = ViewModelProviders.of(this, viewModelFactory).get(CountriesViewModel::class.java).apply {
            init()
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCountries.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CountriesFragment.context)
            adapter = CountryAdapter{ navController().navigate(R.id.action_countriesFragment_to_detailsFragment) }
        }

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        binding.viewmodel?.filter(s.toString())
    }

    fun navController() = findNavController()

}
