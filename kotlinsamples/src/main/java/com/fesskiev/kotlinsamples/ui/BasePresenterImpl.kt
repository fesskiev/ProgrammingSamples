package com.fesskiev.kotlinsamples.ui

import com.fesskiev.kotlinsamples.domain.entity.ErrorResponse
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


open class BasePresenterImpl(private var compositeDisposable: CompositeDisposable,
                             private var view: BaseView) : BasePresenter {

    override fun detach() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun showError(e: Throwable) {
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