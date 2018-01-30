package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class Image(@SerializedName("#text")
                 val text: String = "",
                 @SerializedName("size")
                 val size: String = "")