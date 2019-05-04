package com.clakestudio.pc.countries.ui.countires

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.adapters.countries.CountryAdapter
import com.clakestudio.pc.countries.databinding.CountriesFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import javax.inject.Inject


class CountriesFragment : Fragment(), Injectable {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            adapter = CountryAdapter { navController().navigate(R.id.action_countriesFragment_to_detailsFragment) }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val searchView: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewmodel?.filter(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.viewmodel?.filter(newText!!)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun navController() = findNavController()

}
