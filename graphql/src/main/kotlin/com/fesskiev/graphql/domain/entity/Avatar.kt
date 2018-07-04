package com.fesskiev.graphql.domain.entity

import com.google.gson.annotations.SerializedName

data class Avatar(@SerializedName("url")
                  var url: String? = null)
