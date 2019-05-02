package com.clakestudio.pc.countries.di

import com.clakestudio.pc.countries.ui.countires.CountriesFragment
import com.clakestudio.pc.countries.ui.details.DetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCountriesFragment(): CountriesFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

}