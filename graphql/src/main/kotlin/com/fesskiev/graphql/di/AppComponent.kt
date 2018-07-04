package com.fesskiev.graphql.di

import android.app.Application
import com.fesskiev.graphql.App

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, NetworkModule::class,
    UtilsModule::class, DataSourceModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
