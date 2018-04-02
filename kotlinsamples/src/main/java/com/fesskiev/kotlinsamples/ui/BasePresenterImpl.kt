package com.fesskiev.kotlinsamples.ui

import com.fesskiev.kotlinsamples.domain.entity.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.io.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException


open class BasePresenterImpl(private var view: BaseView) : BasePresenter {

    override fun detach() {

    }

    fun showError(e: Exception) {
        if (e is HttpException) {
            val responseBody = e.response().errorBody()
            view.showResponseError(getErrorMessage(responseBody))
        } else if (e is SocketTimeoutException) {
            view.showTimeoutError()
        } else if (e is IOException) {
            view.showNetworkError()
        } else {
            view.showUnknownError()
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return Gson().fromJson(responseBody?.string(), ErrorResponse::class.java).message
    }
}