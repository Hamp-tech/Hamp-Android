package com.hamp.mvp.login

import com.hamp.R
import com.hamp.common.BasePresenter
import com.hamp.common.BaseView

interface LoginContract {
    interface View : BaseView {
        fun loginSucceed()
        fun showLoginError(message: String)
        fun showError(message: Int = R.string.generic_error)
    }

    interface Presenter : BasePresenter<View> {
        fun login(email: String, password: String)
    }
}