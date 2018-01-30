package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.source.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
abstract class TopTracksModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideTopTracksPresenter(compositeDisposable: CompositeDisposable,
                                      dataRepository: DataRepository,
                                      view: TopTracksContract.View): TopTracksPresenter {
            return TopTracksPresenter(compositeDisposable, dataRepository, view)
        }

        @JvmStatic
        @Provides
        fun provideCompositeDisposable(): CompositeDisposable {
            return CompositeDisposable()
        }
    }

    @Binds
    abstract fun provideTopTracksView(topTracksActivity: TopTracksActivity): TopTracksContract.View

}