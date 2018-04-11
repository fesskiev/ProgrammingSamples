package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(@SerializedName("error")
                 val error: Int = 0,
                 @SerializedName("message")
                 val message: String = "")