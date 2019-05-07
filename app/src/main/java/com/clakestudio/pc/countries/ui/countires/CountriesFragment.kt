package com.clakestudio.pc.countries.ui.countires

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.adapters.countries.CountriesAdapter
import com.clakestudio.pc.countries.databinding.CountriesFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.countries_fragment.*
import javax.inject.Inject


@OpenForTesting
class CountriesFragment : Fragment(), Injectable, SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        binding.viewmodel?.load()
    }

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

        displaySnackBack("ELO")
        binding.viewmodel = ViewModelProviders.of(this, viewModelFactory).get(CountriesViewModel::class.java).apply {
            load()
            navigationLiveEvent.observe(viewLifecycleOwner, Observer {
                navigate(it)
            })

            error.observe(viewLifecycleOwner, Observer {
                text_view_error.text = it
            })
            loading.observe(viewLifecycleOwner, Observer {
                swipe_refresh_layout.isRefreshing = it
            })
            message.observe(viewLifecycleOwner, Observer {
                displaySnackBack(it)
            })
        }
        swipe_refresh_layout.setOnRefreshListener(this)

    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCountries.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CountriesFragment.context)
            adapter =
                CountriesAdapter { binding.viewmodel?.exposeNavigationDestinationCode(it) }
        }

    }

    fun navigate(destination: String) {
        Log.e("Name", destination)
        val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment()
        action.alpha = destination
        navController().navigate(action)
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

    private fun displaySnackBack(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    fun navController() = findNavController()

}
