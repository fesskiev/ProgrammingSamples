package com.fesskiev.kotlinsamples.domain.entity

import com.google.gson.annotations.SerializedName

data class Track(@SerializedName("duration")
                 val duration: String = "",
                 @SerializedName("image")
                 val image: List<Image> = ArrayList(),
                 @SerializedName("mbid")
                 val mbid: String = "",
                 @SerializedName("listeners")
                 val listeners: String = "",
                 @SerializedName("streamable")
                 val streamable: Streamable =  Streamable(),
                 @SerializedName("artist")
                 val artist: Artist = Artist(),
                 @SerializedName("playcount")
                 val playCount: String = "",
                 @SerializedName("name")
                 val name: String = "",
                 @SerializedName("url")
                 val url: String = "")