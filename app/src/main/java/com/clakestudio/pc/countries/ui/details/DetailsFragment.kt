package com.clakestudio.pc.countries.ui.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clakestudio.pc.countries.adapters.details.DetailAdapter
import com.clakestudio.pc.countries.databinding.DetailsFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import kotlinx.android.synthetic.main.details_fragment.*
import javax.inject.Inject


class DetailsFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DetailsFragmentBinding

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        setUpRecyclerView()
        // TODO: Use the ViewModel
    }

    private fun setUpRecyclerView() {
        recycler_view_details.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DetailsFragment.context)
            adapter = DetailAdapter(arrayListOf(Pair("Area", "12312312"), Pair("Density", "12/km")))
        }

    }

    fun navController() = findNavController()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
