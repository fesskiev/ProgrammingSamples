package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataRepository
import com.fesskiev.kotlinsamples.ui.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopTracksPresenter(var compositeDisposable: CompositeDisposable,
                         var dataRepository: DataRepository,
                         var view: TopTracksContract.View) : BasePresenterImpl(compositeDisposable, view),
        TopTracksContract.Presenter {

    override fun getTopTracks() {
        compositeDisposable.add(dataRepository.getTopTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showTopTracks, this::showError))
    }

    private fun showTopTracks(topTracks: TopTracks) {
        view.showTopTracks(topTracks.tracks.trackList)
    }
}