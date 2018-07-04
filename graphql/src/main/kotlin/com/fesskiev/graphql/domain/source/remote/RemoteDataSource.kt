package com.fesskiev.graphql.domain.source.remote

import android.content.Context
import com.fesskiev.graphql.R
import com.fesskiev.graphql.domain.entity.UserResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import java.util.LinkedHashMap


class RemoteDataSource(private val context: Context,
                       retrofit: Retrofit) : DataSource {

    private val service: ApiService = retrofit.create(ApiService::class.java)

    override fun getUser(): Deferred<UserResponse> {
        return service.getUser(queryUserDocument())
    }

    private fun queryUserDocument(): Map<String, String> {
        val data = LinkedHashMap<String, String>()
        data["query"] = context.getString(R.string.user_document)
        return data
    }

}