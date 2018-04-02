package com.fesskiev.kotlinsamples.domain.source

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.remote.RemoteDataSource
import kotlinx.coroutines.experimental.Deferred

class DataRepository(private val remoteSource: RemoteDataSource) : DataSource {

    override fun getTopTracks(): Deferred<TopTracks> {
        return remoteSource.getTopTracks()
    }
}