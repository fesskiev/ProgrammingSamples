package com.fesskiev.kotlinsamples.domain.source

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import io.reactivex.Single


interface DataSource {

    fun getTopTracks(): Single<TopTracks>
}