package com.fesskiev.graphql.ui.user

import com.fesskiev.graphql.domain.source.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class UserModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideRepoOwnerPresenter(dataRepository: DataRepository, view: UserContract.View): UserPresenter {
            return UserPresenter(dataRepository, view)
        }
    }

    @Binds
    abstract fun provideUserView(topTracksActivity: UserActivity): UserContract.View
}