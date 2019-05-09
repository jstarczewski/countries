package com.clakestudio.pc.countries.ui.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.clakestudio.pc.countries.adapters.details.DetailAdapter
import com.clakestudio.pc.countries.databinding.DetailsFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import kotlinx.android.synthetic.main.details_fragment.*
import javax.inject.Inject
import com.ahmadrosid.svgloader.SvgLoader
import com.clakestudio.pc.countries.R
import com.clakestudio.pc.countries.testing.OpenForTesting
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar


private const val MAP_VIEW_BUNDLE_KEY = "MAP_BUNDLE_KEY"

@OpenForTesting
class DetailsFragment : Fragment(), Injectable, OnMapReadyCallback, SwipeRefreshLayout.OnRefreshListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java).apply {
            binding.viewmodel = this
            countryFlagUrl.observe(viewLifecycleOwner, Observer {
                setFlag(it)
            })
            latlng.observe(viewLifecycleOwner, Observer {
                map_view_country.getMapAsync(this@DetailsFragment)
            })
            /*
            error.observe(viewLifecycleOwner, Observer {
                text_view_name.text = it
                showWidgets(it.isNotEmpty())
            })*/
            loading.observe(viewLifecycleOwner, Observer {
                swipe_refresh_layout.isRefreshing = it
            })
            message.observe(viewLifecycleOwner, Observer {
                displaySnackBack(it)
            })
        }
        initFromBundle()
        setUpRecyclerView()
        swipe_refresh_layout.setOnRefreshListener(this)
        setUpGoogleMaps(savedInstanceState)
    }

    private fun setUpRecyclerView() {
        recycler_view_details.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailsFragment.context)
            adapter = DetailAdapter()
        }
    }

    private fun initFromBundle() = arguments?.let {
        viewModel.load(DetailsFragmentArgs.fromBundle(it).alpha)
    }

    fun setUpGoogleMaps(savedInstanceState: Bundle?) {
        val mapViewBundle: Bundle? = savedInstanceState?.getBundle(MAP_VIEW_BUNDLE_KEY)
        map_view_country.onCreate(mapViewBundle)
        map_view_country.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        /**
         * Not sure whether this is needed as pointed out in tutorial, because we are using ViewModel
         * and Map data changes every time we change country, bo I will leave it this way, because
         * that is just a bundle to save state and even if it is useless it does not harm the code
         * */

        val mapViewBundle: Bundle? = outState.getBundle(MAP_VIEW_BUNDLE_KEY) ?: Bundle()
        outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        map_view_country.onSaveInstanceState(mapViewBundle)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun setFlag(url: String) {
        SvgLoader.pluck()
            .with(activity)
            .setPlaceHolder(R.drawable.ic_file_download_black_24dp, R.drawable.ic_error_outline_black_24dp)
            .load(url, image_view_flag)

    }

    override fun onMapReady(p0: GoogleMap?) {
        val pair = viewModel.latlng.value
        if (pair?.first != null && pair.second != null) {
            p0?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(pair.first!!, pair.second!!)))
            p0?.moveCamera(CameraUpdateFactory.zoomTo(5f))
        }
    }

    /*
    private fun showWidgets(isError: Boolean) {
        recycler_view_details.visibility = if (isError) View.GONE else View.VISIBLE
        map_view_country.visibility = if (isError) View.GONE else View.VISIBLE
        image_view_flag.visibility = if (isError) View.GONE else View.VISIBLE
    }*/

    override fun onResume() {
        super.onResume()
        map_view_country.onResume()
    }

    override fun onStart() {
        super.onStart()
        map_view_country.onStart()
    }


    override fun onPause() {
        super.onPause()
        map_view_country.onPause()
    }

    override fun onStop() {
        super.onStop()
        map_view_country.onStop()
    }

    override fun onDestroyView() {
        map_view_country.onDestroy()
        super.onDestroyView()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view_country.onLowMemory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun displaySnackBack(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    fun navController() = findNavController()


}
