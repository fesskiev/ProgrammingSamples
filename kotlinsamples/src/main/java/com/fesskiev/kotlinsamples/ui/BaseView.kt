package com.fesskiev.kotlinsamples.ui


interface BaseView {

    fun showProgressView()

    fun hideProgressView()

    fun showResponseError(message: String)

    fun showTimeoutError()

    fun showNetworkError()

    fun showUnknownError()

    fun showProgressBar()

    fun hideProgressBar()
}