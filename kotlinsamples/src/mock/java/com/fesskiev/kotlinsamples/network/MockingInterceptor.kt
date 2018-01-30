package com.fesskiev.kotlinsamples.network

import android.os.SystemClock

import java.util.Random

import okhttp3.Interceptor
import okhttp3.Response


class MockingInterceptor(private val requestsHandler: RequestsHandler,
                         private val random: Random) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val path = request.url().encodedQuery()
        if (requestsHandler.shouldIntercept(path!!)) {
            val response = requestsHandler.proceed(request, path)
            val stubDelay = (500 + random.nextInt(200)).toLong()
            SystemClock.sleep(stubDelay)
            return response
        }
        return chain.proceed(request)
    }
}
