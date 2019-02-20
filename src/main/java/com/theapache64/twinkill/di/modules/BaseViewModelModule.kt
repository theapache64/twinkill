package com.theapache64.twinkill.di.modules

import androidx.lifecycle.ViewModelProvider
import com.theapache64.twinkill.utils.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}