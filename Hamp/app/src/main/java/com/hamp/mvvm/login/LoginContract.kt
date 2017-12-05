package com.hamp.mvvm.login

import com.hamp.common.BasePresenter
import com.hamp.common.BaseView

interface LoginContract {
    interface View : BaseView {
        fun loginSucceed()
        fun loginError(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun login(email: String, password: String)
    }
}