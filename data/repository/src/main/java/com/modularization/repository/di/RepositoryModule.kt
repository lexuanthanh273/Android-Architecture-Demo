package com.modularization.repository.di

import com.modularization.repository.AppDispatchers
import com.modularization.repository.UserRepository
import com.modularization.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module

val repositoryModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { UserRepositoryImpl(get(), get()) as UserRepository }
}