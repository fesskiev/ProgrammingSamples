package com.fesskiev.kotlinsamples.domain.source.remote

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import kotlinx.coroutines.experimental.Deferred

interface DataSource {

    fun getTopTracks(): Deferred<TopTracks>
}