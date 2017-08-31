package com.hamp.ui.signup

import com.hamp.common.BasePresenter
import com.hamp.common.BaseView

interface SignUpContract {
    interface View : BaseView {
        fun signUpSucceed()
        fun signUpError(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun signUp(email: String, password: String)
    }
}