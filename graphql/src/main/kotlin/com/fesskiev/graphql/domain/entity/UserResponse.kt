package com.fesskiev.graphql.domain.entity

import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("data")
                        var data: Data = Data())
