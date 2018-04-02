package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.source.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class TopTracksModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideTopTracksPresenter(
                                      dataRepository: DataRepository, view: TopTracksContract.View): TopTracksPresenter {
            return TopTracksPresenter( dataRepository, view)
        }
    }

    @Binds
    abstract fun provideTopTracksView(topTracksActivity: TopTracksActivity): TopTracksContract.View

}