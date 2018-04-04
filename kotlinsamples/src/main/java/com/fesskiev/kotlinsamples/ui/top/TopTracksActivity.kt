package com.fesskiev.kotlinsamples.ui.top

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.fesskiev.kotlinsamples.R
import com.fesskiev.kotlinsamples.domain.entity.Track
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_top_tracks.*
import javax.inject.Inject

class TopTracksActivity : DaggerAppCompatActivity(), TopTracksContract.View {

    @Inject
    @JvmField
    var presenter: TopTracksPresenter? = null

    private lateinit var adapter: TopTracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tracks)
        setSupportActionBar(toolbar)
        setRecyclerView()
        fetchTopTracksButton.setOnClickListener({
            presenter?.getTopTracks()
        })
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = TopTracksAdapter()
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

    override fun showTopTracks(tracks: List<Track>?) {
        if (tracks != null) {
            adapter.refresh(tracks)
        }
    }

    override fun showProgressView() {
        progressBar.visibility = VISIBLE
    }

    override fun hideProgressView() {
        progressBar.visibility = GONE
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
}
