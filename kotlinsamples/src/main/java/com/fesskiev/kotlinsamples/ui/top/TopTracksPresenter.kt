package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataRepository
import com.fesskiev.kotlinsamples.ui.BasePresenterImpl
import kotlinx.coroutines.experimental.launch

class TopTracksPresenter(private var dataRepository: DataRepository,
                         private var view: TopTracksContract.View) : BasePresenterImpl(view), TopTracksContract.Presenter {

    override fun getTopTracks() {
        showProgressBar()
        job = launch {
            try {
                val topTracks = dataRepository.getTopTracks().await()
                showTopTracks(topTracks)
            } catch (e: Exception) {
                e.printStackTrace()
                showError(e)
            } finally {
                hideProgressBar()
            }
        }
    }

    override fun showCachedResponse() {

    }

    private fun showTopTracks(topTracks: TopTracks) {
        view.showTopTracks(topTracks.tracks.trackList)
    }
}