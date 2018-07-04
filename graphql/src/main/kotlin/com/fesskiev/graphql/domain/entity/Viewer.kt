package com.fesskiev.graphql.domain.entity

import com.google.gson.annotations.SerializedName

data class Viewer(@SerializedName("user")
                   var user: User = User())
