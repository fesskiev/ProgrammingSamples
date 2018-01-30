package com.fesskiev.kotlinsamples.domain.entity


import com.google.gson.annotations.SerializedName

data class TopTracks(@SerializedName("tracks")
                     val tracks: Tracks = Tracks())
