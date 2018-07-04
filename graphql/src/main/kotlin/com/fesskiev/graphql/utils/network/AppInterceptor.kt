package com.fesskiev.graphql.utils.network

import okhttp3.*

class AppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = getModifiedRequest(chain.request())
        return chain.proceed(request!!)
    }

    private fun getModifiedRequest(original: Request?): Request? {
        val builder = original?.newBuilder()
        builder?.addHeader("Authorization", "Bearer 178a75317e28d4df1e3aafe0241d1b2c3c77879c")
        builder?.addHeader("Accept-Encoding", "identity")
        return builder?.build()
    }

}