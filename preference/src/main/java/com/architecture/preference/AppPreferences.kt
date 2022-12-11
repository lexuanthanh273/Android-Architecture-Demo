package com.architecture.preference

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val moshi: Moshi
) {
    companion object {
        private const val SHARED_NAME = "sl_shared"
        private const val KEY_SORT = "KEY_SORT"
    }

    private val sharedPreferences: SharedPreferences =
        applicationContext.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)


    fun setAggregationBoardSeparate(isSeparate: Boolean) {
        sharedPreferences[KEY_SORT] = isSeparate
    }

}