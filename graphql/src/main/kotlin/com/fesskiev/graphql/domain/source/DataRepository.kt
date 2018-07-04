package com.fesskiev.graphql.domain.source

import com.fesskiev.graphql.domain.entity.UserResponse
import com.fesskiev.graphql.domain.source.remote.DataSource
import com.fesskiev.graphql.domain.source.remote.RemoteDataSource
import kotlinx.coroutines.experimental.Deferred


class DataRepository(private val remoteSource: RemoteDataSource) : DataSource {

    override fun getUser(): Deferred<UserResponse> {
        return remoteSource.getUser()
    }
}