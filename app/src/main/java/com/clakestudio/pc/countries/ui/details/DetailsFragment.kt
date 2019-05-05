package com.clakestudio.pc.countries.ui.details

import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clakestudio.pc.countries.adapters.details.DetailAdapter
import com.clakestudio.pc.countries.databinding.DetailsFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import kotlinx.android.synthetic.main.details_fragment.*
import javax.inject.Inject
import com.ahmadrosid.svgloader.SvgLoader
import com.clakestudio.pc.countries.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


private const val MAP_VIEW_BUNDLE_KEY = "MAP_BUNDLE_KEY"

class DetailsFragment : Fragment(), Injectable, OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        binding.viewmodel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java).apply {
            countryFlagUrl.observe(viewLifecycleOwner, Observer {
                setFlag(it)
            })
            latlng.observe(viewLifecycleOwner, Observer {

            })
        }
        arguments?.let {
            binding.viewmodel?.getDataByName(DetailsFragmentArgs.fromBundle(it).alpha)
        }
        setUpRecyclerView()
        setUpGoogleMaps(savedInstanceState)
    }

    private fun setUpRecyclerView() {
        recycler_view_details.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailsFragment.context)
            adapter = DetailAdapter()
        }
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
            .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
            .load(url, image_view_flag)

    }


    override fun onMapReady(p0: GoogleMap?) {
        val pair = binding.viewmodel?.latlng?.value
        if (pair?.first != null && pair.second != null) {
            p0?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(pair.first!!, pair.second!!)))
            p0?.moveCamera(CameraUpdateFactory.zoomTo(5f))
        }
    }

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


}
