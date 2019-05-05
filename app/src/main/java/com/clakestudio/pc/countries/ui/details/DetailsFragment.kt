package com.clakestudio.pc.countries.ui.details

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
                map_view_country.visibility = View.VISIBLE
            })
        }
        arguments?.let {
            Log.e("code", DetailsFragmentArgs.fromBundle(it).alpha)
            binding.viewmodel?.getDataByName(DetailsFragmentArgs.fromBundle(it).alpha)
        }
        map_view_country.onCreate(savedInstanceState)
        map_view_country.getMapAsync(this)
        setUpRecyclerView()
        //    setFlag()
        // TODO: Use the ViewModel
    }



    private fun setUpRecyclerView() {
        recycler_view_details.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailsFragment.context)
            adapter = DetailAdapter()
        }
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
        p0?.moveCamera(
            CameraUpdateFactory
                .newLatLng(LatLng(pair!!.first, pair.second))
        )
        p0?.moveCamera(
            CameraUpdateFactory.zoomTo(5f)
        )
    }

    fun moveCamera(latlng: LatLng) {


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
