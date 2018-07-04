package com.fesskiev.graphql.domain.entity

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("name")
                var name: String = "",
                @SerializedName("avatar")
                var avatar: Avatar = Avatar())



