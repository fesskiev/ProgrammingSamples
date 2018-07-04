package com.fesskiev.graphql.di

import android.content.Context
import com.fesskiev.graphql.utils.InternetManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideInternetManager(context: Context): InternetManager {
        return InternetManager(context)
    }
}