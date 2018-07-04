package com.fesskiev.graphql.ui.user

import com.fesskiev.graphql.domain.source.DataRepository
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class UserPresenter(private val dataRepository: DataRepository,
                    private val view: UserContract.View) : UserContract.Presenter {

    private var job: Job? = null

    override fun fetchUser() {
        view.showProgressView()
        job = launch {
            try {
                val userResponse = dataRepository.getUser().await()
                view.showUser(userResponse.data.viewer.user)
            } catch (e: Exception) {
                e.printStackTrace()
                showError(e)
            } finally {
                view.hideProgressView()
            }
        }
    }

    private fun showError(e: Exception) {
        when (e) {
            is HttpException -> handleHttpException(e)
            is SocketTimeoutException -> view.showTimeoutError()
            is IOException -> view.showNetworkError()
            else -> view.showUnknownError()
        }
    }

    private fun handleHttpException(e: HttpException) {
        val code = e.code()
        if (code == 304) {
            getRepoOwnerFromCache()
        } else {
            val responseBody = e.response().errorBody()
        }
    }

    private fun getRepoOwnerFromCache() {

    }

    override fun detach() {
        job?.cancel()
    }
}