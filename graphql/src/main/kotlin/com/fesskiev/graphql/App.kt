package com.fesskiev.graphql

import com.facebook.FacebookSdk
import com.fesskiev.graphql.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(this);
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}