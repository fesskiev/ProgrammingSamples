package com.fesskiev.kotlinsamples.ui.top

import com.fesskiev.kotlinsamples.domain.entity.Track
import com.fesskiev.kotlinsamples.ui.BaseView

class TopTracksContract {

    interface View : BaseView {

        fun showTopTracks(tracks: List<Track>)
    }

    interface Presenter {

        fun getTopTracks()
    }
}
