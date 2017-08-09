package com.hamp.mvp.login

import com.google.firebase.auth.FirebaseAuth

class LoginPresenter : LoginContract.Presenter {

    private val TAG = LoginPresenter::class.java.simpleName

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit private var mView: LoginContract.View

    override fun setView(view: LoginContract.View) {
        mView = view
    }

    override fun login(email: String, password: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}