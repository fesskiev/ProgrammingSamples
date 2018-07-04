package com.fesskiev.graphql.domain.entity

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("viewer")
                var viewer: Viewer = Viewer())
