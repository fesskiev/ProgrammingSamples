package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataRepository
import com.fesskiev.kotlinsamples.ui.BasePresenterImpl
import kotlinx.coroutines.experimental.async

class TopTracksPresenter(var dataRepository: DataRepository,
                         var view: TopTracksContract.View) : BasePresenterImpl(view), TopTracksContract.Presenter {

    override fun getTopTracks() {
        async {
            try {
                val topTracks = dataRepository.getTopTracks().await()
                showTopTracks(topTracks)
            } catch (e: Exception) {
                showError(e)
            }
        }
    }

    private fun showTopTracks(topTracks: TopTracks) {
        view.showTopTracks(topTracks.tracks.trackList)
    }
}