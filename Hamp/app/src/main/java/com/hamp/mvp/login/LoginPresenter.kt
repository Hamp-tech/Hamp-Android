package com.hamp.mvp.login

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.hamp.R
import com.hamp.prefs

class LoginPresenter : LoginContract.Presenter {

    private val TAG = LoginPresenter::class.java.simpleName

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit private var mView: LoginContract.View

    override fun setView(view: LoginContract.View) {
        mView = view
    }

    override fun login(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (mView.isActive()) mView.showProgress(false)
                    if (task.isSuccessful) {
                        val firebaseUser = task.result.user
                        prefs.userId = firebaseUser.uid
                        firebaseUser.email?.let { prefs.email = it }
                        mView.loginSucceed()
                    } else {
                        when (task.exception) {
                            is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                            is FirebaseAuthInvalidCredentialsException -> mView.showLoginError(task.exception?.message!!)
                            else -> mView.showError()
                        }
                    }
                }
    }
}