package com.modularization.smartapp.di

import com.modularization.detail.di.featureDetailModule
import com.modularization.home.di.featureHomeModule
import com.modularization.local.di.localModule
import com.modularization.remote.di.createRemoteModule
import com.modularization.repository.di.repositoryModule

val appComponent= listOf(createRemoteModule("https://api.github.com/"),
    repositoryModule,
    featureHomeModule,
    featureDetailModule,
    localModule)