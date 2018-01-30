package com.fesskiev.kotlinsamples.network

import android.content.Context
import okhttp3.*

import java.io.IOException
import java.io.InputStream
import java.util.HashMap

import okio.Buffer

class RequestsHandler(private val context: Context) {

    private val responsesMap = HashMap<String, String>()
    private val topTracksKey = "method=chart.gettoptracks&api_key=adc1430e56411c85fadf04c03b54068f&format=json"

    init {
        initResponsesMap()
    }

    private fun initResponsesMap() {
        responsesMap[topTracksKey] = "getTopTracks.json"
//        responsesMap[topTracksKey] = "getError.json"
    }

    fun shouldIntercept(path: String): Boolean {
        val keys = responsesMap.keys
        for (interceptUrl in keys) {
            if (path.contains(interceptUrl)) {
                return true
            }
        }
        return false
    }

    fun proceed(request: Request, path: String): Response {
        val keys = responsesMap.keys
        for (interceptUrl in keys) {
            if (path.contains(interceptUrl)) {
                val mockResponsePath = responsesMap[interceptUrl]
                return createSuccessResponse(request, mockResponsePath)
//                return createErrorResponse(request, mockResponsePath)
            }
        }
        return error(request, 500, "Incorrectly intercepted request")
    }

    private fun createSuccessResponse(request: Request, assetPath: String?): Response {
        val stream : InputStream? = null
        try {
            val stream = context.assets.open(assetPath)
            return successResponse(request, stream)
        } catch (e: IOException) {
            e.printStackTrace()
            return error(request, 500, e.message!!)
        } finally {
            stream?.close()
        }
    }

    private fun createErrorResponse(request: Request, assetPath: String?): Response {
        val stream : InputStream? = null
        try {
            val stream = context.assets.open(assetPath)
            return errorResponse(request, stream)
        } catch (e: IOException) {
            e.printStackTrace()
            return error(request, 500, e.message!!)
        } finally {
            stream?.close()
        }
    }

    private fun errorResponse(request: Request, stream: InputStream): Response {
        val buffer = Buffer().readFrom(stream)
        return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(403)
                .message("Forbidden")
                .body(ResponseBody.create(MediaType.parse("application/json"), buffer.size(), buffer))
                .build()
    }

    private fun successResponse(request: Request, stream: InputStream): Response {
        val buffer = Buffer().readFrom(stream)
        return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MediaType.parse("application/json"), buffer.size(), buffer))
                .build()
    }

    private fun error(request: Request, code: Int, message: String): Response {
        return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(message)
                .body(ResponseBody.create(MediaType.parse("application/json"), ByteArray(0)))
                .build()
    }
}
