package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(@SerializedName("error")
                 var error: Int = 0,
                 @SerializedName("message")
                 var message: String = "")