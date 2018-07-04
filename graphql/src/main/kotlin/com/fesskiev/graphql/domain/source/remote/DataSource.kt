package com.fesskiev.graphql.domain.source.remote

import com.fesskiev.graphql.domain.entity.UserResponse
import kotlinx.coroutines.experimental.Deferred

interface DataSource {

    fun getUser(): Deferred<UserResponse>
}