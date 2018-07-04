package com.fesskiev.kotlinsamples.ui.top

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View.*
import com.fesskiev.kotlinsamples.R
import com.fesskiev.kotlinsamples.domain.entity.Track
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class TopTracksActivity : DaggerAppCompatActivity(), TopTracksContract.View {

    @Inject
    @JvmField
    var presenter: TopTracksPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fetchTopTracksButton.setOnClickListener {
            presenter?.getTopTracks()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

    override fun showTopTracks(tracks: List<Track>?) {
        tracks?.iterator()?.forEach {
            Log.wtf("track", it.toString())
        }
    }

    override fun showResponseError(message: String) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showTimeoutError() {
        Snackbar.make(coordinatorLayout, "Timeout Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showNetworkError() {
        Snackbar.make(coordinatorLayout, "Network Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showUnknownError() {
        Snackbar.make(coordinatorLayout, "Unknown Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        progressBar.visibility = VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = INVISIBLE
    }
}
