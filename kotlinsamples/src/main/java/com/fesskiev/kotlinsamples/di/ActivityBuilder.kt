package com.fesskiev.kotlinsamples.di

import com.fesskiev.kotlinsamples.ui.top.TopTracksActivity
import com.fesskiev.kotlinsamples.ui.top.TopTracksModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(TopTracksModule::class)])
    abstract fun bindTopTracksActivity(): TopTracksActivity
}
