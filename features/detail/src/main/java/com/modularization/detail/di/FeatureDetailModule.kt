package com.modularization.detail.di

import com.modularization.detail.DetailImageViewModel
import com.modularization.detail.DetailViewModel
import com.modularization.detail.domain.GetUserDetailUseCase
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val featureDetailModule = module {
    factory { GetUserDetailUseCase(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DetailImageViewModel() }
}