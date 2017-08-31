package com.hamp.ui.login

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.hamp.R
import com.hamp.auth
import com.hamp.prefs

class LoginPresenter : LoginContract.Presenter {

    private val TAG = LoginPresenter::class.java.simpleName

    lateinit private var mView: LoginContract.View

    override fun setView(view: LoginContract.View) {
        mView = view
    }

    override fun login(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.loginWithEmailAndPassword(email, password, OnCompleteListener<AuthResult> { task ->
            if (mView.isActive()) mView.showProgress(false)
            if (task.isSuccessful) {
                val firebaseUser = task.result.user
                prefs.userId = firebaseUser.uid
                firebaseUser.email?.let { prefs.email = it }
                mView.loginSucceed()
            } else {
                when (task.exception) {
                    is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                    is FirebaseAuthInvalidCredentialsException -> mView.loginError(task.exception?.message!!)
                    else -> mView.showError()
                }
            }
        })
    }
}