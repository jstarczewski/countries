package com.clakestudio.pc.countries.ui.countires

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
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
class CountriesFragment : Fragment(), Injectable, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: CountriesFragmentBinding
    lateinit var viewModel: CountriesViewModel


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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CountriesViewModel::class.java).apply {
            binding.viewmodel = this
            init()

            navigationLiveEvent.observe(viewLifecycleOwner, Observer {
                navigate(it)
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
                CountriesAdapter { viewModel.exposeNavigationDestinationCode(it) }
        }

    }

    fun navigate(destination: String) {
        val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment()
        action.alpha = destination
        navController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.filter(query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.filter(newText!!)
        return false
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun displaySnackBack(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    fun navController() = findNavController()

}
