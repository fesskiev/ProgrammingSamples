package com.fesskiev.graphql.ui.user

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.fesskiev.graphql.R
import com.fesskiev.graphql.domain.entity.User
import dagger.android.DaggerActivity
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

/**
 *  GraphQL API references
 *  https://graphql.buildkite.com/explorer
 *  https://building.buildkite.com/tutorial-getting-started-with-graphql-queries-and-mutations-11211dfe5d64
 */
class UserActivity : DaggerActivity(), UserContract.View {

    @Inject
    @JvmField
    var presenter: UserPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setupFAB()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    private fun setupFAB() {
        fab.setOnClickListener { presenter?.fetchUser() }
    }

    override fun onPause() {
        super.onPause()
        presenter?.detach()
    }


    override fun showUser(user: User) {
        Log.e("test", "user: " + user.toString())
    }

    override fun showProgressView() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressView() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showResponseError(message: String) {
        Snackbar.make(constraintLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showTimeoutError() {
        Snackbar.make(constraintLayout, "Timeout Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showNetworkError() {
        Snackbar.make(constraintLayout, "Network Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showUnknownError() {
        Snackbar.make(constraintLayout, "Unknown Error!", Snackbar.LENGTH_LONG).show()
    }

    override fun showNoNetworkError() {
        Snackbar.make(constraintLayout, "No Network Error!", Snackbar.LENGTH_LONG).show()
    }
}
