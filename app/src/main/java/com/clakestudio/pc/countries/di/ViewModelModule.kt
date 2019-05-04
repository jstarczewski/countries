package com.clakestudio.pc.countries.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.clakestudio.pc.countries.ui.countires.CountriesViewModel
import com.clakestudio.pc.countries.ui.details.DetailsViewModel
import com.clakestudio.pc.countries.viewmodel.CountriesViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    @Singleton
    abstract fun bindDetailsViewMode(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CountriesViewModelFactory): ViewModelProvider.Factory

}