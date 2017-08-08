package com.hamp.mvp.signup

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.hamp.R

class SignUpPresenter : SignUpContract.Presenter {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val TAG = SignUpPresenter::class.java.simpleName
    lateinit private var mView: SignUpContract.View

    override fun setView(view: SignUpContract.View) {
        mView = view
    }

    override fun signUp(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

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