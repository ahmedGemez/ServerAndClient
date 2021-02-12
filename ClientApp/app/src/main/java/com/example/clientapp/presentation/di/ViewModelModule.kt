package com.example.clientapp.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clientapp.presentation.ui.homefragment.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindBaseViewModel(viewModel: HomeViewModel): ViewModel



}