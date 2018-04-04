package com.fesskiev.kotlinsamples.ui.top

import android.os.Looper
import android.util.Log
import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataRepository
import com.fesskiev.kotlinsamples.ui.BasePresenterImpl
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class TopTracksPresenter(var dataRepository: DataRepository,
                         var view: TopTracksContract.View) : BasePresenterImpl(view), TopTracksContract.Presenter {

    private var job: Job? = null

    override fun getTopTracks() {
        job = launch(UI) {
            try {
                Log.wtf("test", "is main async: ${isMainThread()}")
                val topTracks = dataRepository.getTopTracks().await()
                showTopTracks(topTracks)
            } catch (e: Exception) {
                showError(e)
            }
        }
    }

    override fun detach() {
        super.detach()
        job?.cancel()
    }

    private fun showTopTracks(topTracks: TopTracks) {
        Log.wtf("test", "is main show: ${isMainThread()}")
        view.showTopTracks(topTracks.tracks.trackList)
    }

    private fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}