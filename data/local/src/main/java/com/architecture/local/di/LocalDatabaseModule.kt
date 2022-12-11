package com.architecture.local.di

import android.content.Context
import com.architecture.local.SLAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext application: Context): SLAppDatabase {
        return SLAppDatabase.buildDatabase(application)
    }
//
//    @Provides
//    @Singleton
//    fun provideContactDao(db: SLAppDatabase) = db.getContactDao()
//
//    @Provides
//    @Singleton
//    fun provideSmsDao(db: SLAppDatabase) = db.getSmsDao()
//
//    @Provides
//    @Singleton
//    fun provideContactPhoneNumberDao(db: SLAppDatabase) = db.getContactPhoneNumberDao()

}
