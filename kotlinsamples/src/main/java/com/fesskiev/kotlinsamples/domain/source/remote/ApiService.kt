package com.fesskiev.kotlinsamples.domain.source.remote

import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiService {

    @GET(".")
    fun getTopTracks(@QueryMap options: Map<String, String>): Single<TopTracks>
}