package com.fesskiev.kotlinsamples.ui


interface BaseView {

    fun showResponseError(message: String)

    fun showTimeoutError()

    fun showNetworkError()

    fun showUnknownError()

    fun showProgressBar()

    fun hideProgressBar()
}