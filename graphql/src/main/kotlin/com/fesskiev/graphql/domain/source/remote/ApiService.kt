package com.fesskiev.graphql.domain.source.remote

import com.fesskiev.graphql.domain.entity.UserResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST(".")
    fun getUser(@Body document: Map<String, String>): Deferred<UserResponse>
}