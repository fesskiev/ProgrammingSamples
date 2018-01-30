package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class Tracks(@SerializedName("track")
                  val trackList: List<Track> = ArrayList())