package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataRepository
import com.fesskiev.kotlinsamples.ui.BasePresenterImpl
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class TopTracksPresenter(var dataRepository: DataRepository,
                         var view: TopTracksContract.View?) : BasePresenterImpl(view), TopTracksContract.Presenter {

    private var job: Job? = null

    override fun getTopTracks() {
        job = launch(UI) {
            try {
                showProgressView()
                val topTracks = dataRepository.getTopTracks().await()
                showTopTracks(topTracks)
            } catch (e: Exception) {
                showError(e)
            } finally {
                hideProgressView()
            }
        }
    }

    override fun detach() {
        super.detach()
        job?.cancel()
    }

    private fun showProgressView() {
        view?.showProgressView()
    }

    private fun hideProgressView() {
        view?.hideProgressView()
    }

    private fun showTopTracks(topTracks: TopTracks) {
        view?.showTopTracks(topTracks.tracks.trackList)
    }
}