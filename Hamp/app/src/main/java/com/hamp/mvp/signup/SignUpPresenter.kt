package com.hamp.mvp.signup

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.hamp.R
import com.hamp.prefs

class SignUpPresenter : SignUpContract.Presenter {
    private val TAG = SignUpPresenter::class.java.simpleName

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit private var mView: SignUpContract.View

    override fun setView(view: SignUpContract.View) {
        mView = view
    }

    override fun signUp(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (mView.isActive()) mView.showProgress(false)
                        val firebaseUser = task.result.user
                        prefs.userId = firebaseUser.uid
                        firebaseUser.email?.let { prefs.email = it }
                        mView.signUpSucceed()
                    } else {
                        if (mView.isActive()) mView.showProgress(false)
                        when (task.exception) {
                            is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                            is FirebaseAuthUserCollisionException -> mView.showError(R.string.email_already_exist)
                            else -> mView.showError()
                        }
                    }
                }
    }
}