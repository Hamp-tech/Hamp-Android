package com.hamp.mvp.signup

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.hamp.R
import com.hamp.auth
import com.hamp.prefs

class SignUpPresenter : SignUpContract.Presenter {
    private val TAG = SignUpPresenter::class.java.simpleName

    lateinit private var mView: SignUpContract.View

    override fun setView(view: SignUpContract.View) {
        mView = view
    }

    override fun signUp(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.signUpWithEmailAndPassword(email, password, OnCompleteListener<AuthResult> { task ->
            if (mView.isActive()) mView.showProgress(false)
            if (task.isSuccessful) {
                val firebaseUser = task.result.user
                prefs.userId = firebaseUser.uid
                firebaseUser.email?.let { prefs.email = it }
                mView.signUpSucceed()
            } else {
                when (task.exception) {
                    is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                    is FirebaseAuthUserCollisionException -> mView.showError(R.string.email_already_exist)
                    else -> mView.showError()
                }
            }
        })
    }
}