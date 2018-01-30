package com.fesskiev.kotlinsamples.domain.source

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.remote.RemoteDataSource
import io.reactivex.Single


class DataRepository(private val remoteSource: RemoteDataSource) : DataSource {

    override fun getTopTracks(): Single<TopTracks> {
        return remoteSource.getTopTracks()
    }
}