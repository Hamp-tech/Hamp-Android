package com.hamp.mvp.signup

import com.hamp.R
import com.hamp.common.BasePresenter
import com.hamp.common.BaseView

interface SignUpContract {
    interface View : BaseView {
        fun signUpSucceed()
        fun showSignUpError(message: String)
        fun showError(message: Int = R.string.generic_error)
    }

    interface Presenter : BasePresenter<View> {
        fun signUp(email: String, password: String)
    }
}