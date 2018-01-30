package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class Streamable(@SerializedName("#text")
                      val text: String = "",
                      @SerializedName("fulltrack")
                      val fullTrack: String = "")