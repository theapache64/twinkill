package com.theapache64.twinkill.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.theapache64.twinkill.di.modules.ContextModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class PreferenceModule {

    @Singleton
    @Provides
    fun providePreference(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}