package com.clakestudio.pc.countries.ui.countires

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.clakestudio.pc.countries.databinding.CountriesFragmentBinding
import com.clakestudio.pc.countries.di.Injectable
import javax.inject.Inject


class CountriesFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: CountriesFragmentBinding

    private lateinit var viewModel: CountriesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = CountriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CountriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun navController() = findNavController()

}
