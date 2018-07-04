package com.fesskiev.graphql.ui.user

import com.fesskiev.graphql.domain.entity.User
import com.fesskiev.graphql.ui.BasePresenter
import com.fesskiev.graphql.ui.BaseView

interface UserContract {

    interface View: BaseView {

        fun showUser(user: User)
    }

    interface Presenter: BasePresenter {

        fun fetchUser()
    }
}