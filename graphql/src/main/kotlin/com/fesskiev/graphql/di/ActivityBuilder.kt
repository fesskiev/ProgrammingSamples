package com.fesskiev.graphql.di

import com.fesskiev.graphql.ui.user.UserActivity
import com.fesskiev.graphql.ui.user.UserModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(UserModule::class)])
    abstract fun bindRepoOwnerActivity(): UserActivity
}
