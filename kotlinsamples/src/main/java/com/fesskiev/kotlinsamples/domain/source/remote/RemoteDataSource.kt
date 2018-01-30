package com.fesskiev.kotlinsamples.domain.source.remote

import android.content.Context
import com.fesskiev.kotlinsamples.R
import com.fesskiev.kotlinsamples.domain.entity.TopTracks
import com.fesskiev.kotlinsamples.domain.source.DataSource
import io.reactivex.Single
import retrofit2.Retrofit
import java.util.LinkedHashMap


class RemoteDataSource(private val context: Context, retrofit: Retrofit) : DataSource {

    private val service: ApiService = retrofit.create(ApiService::class.java)

    override fun getTopTracks(): Single<TopTracks> {
        return service.getTopTracks(topTracksParams())
    }

    private fun topTracksParams(): Map<String, String> {
        val data = LinkedHashMap<String, String>()
        data["method"] = "chart.gettoptracks"
        data["api_key"] = context.getString(R.string.last_fm_api_key)
        data["format"] = "json"
        return data
    }
}