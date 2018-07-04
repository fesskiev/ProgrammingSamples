package com.fesskiev.graphql.ui

interface BaseView {

    fun showResponseError(message: String)

    fun showTimeoutError()

    fun showNoNetworkError()

    fun showNetworkError()

    fun showUnknownError()

    fun showProgressView()

    fun hideProgressView()
}