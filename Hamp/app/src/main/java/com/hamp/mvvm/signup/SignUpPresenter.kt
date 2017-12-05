package com.hamp.mvvm.signup

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.hamp.R
import com.hamp.api
import com.hamp.auth
import com.hamp.prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class SignUpPresenter : SignUpContract.Presenter {
    private val TAG = SignUpPresenter::class.java.simpleName

    lateinit private var mView: SignUpContract.View

    override fun setView(view: SignUpContract.View) {
        mView = view
    }

    override fun signUp(email: String, password: String, name: String, surname: String, phone: String,
                        birthday: String, gender: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.signUpWithEmailAndPassword(email, password, OnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = task.result.user
                prefs.userId = firebaseUser.uid
                serverSignUp(name, surname, email, phone, birthday, gender)
            } else {
                if (mView.isActive()) {
                    mView.showProgress(false)
                    when (task.exception) {
                        is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                        is FirebaseAuthUserCollisionException -> mView.showError(R.string.email_already_exist)
                        else -> mView.showError()
                    }
                }
            }
        })
    }

    private fun serverSignUp(name: String, surname: String, email: String, phone: String,
                             birthday: String, gender: String) {
        api.createUserWithID(prefs.userId, name, surname, email, phone, birthday, gender, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (mView.isActive()) {
                                mView.showProgress(false)
                                mView.signUpSucceed()
                            }
                        },
                        onError = { e ->
                            if (mView.isActive()) {
                                mView.showProgress(false)
                                when (e) {
                                    is retrofit2.HttpException -> {
                                        e.response().errorBody()?.let { mView.signUpError(api.convertError(it).message) }
                                    }
                                    is UnknownHostException -> mView.showError(R.string.internet_connection_error)
                                    else -> mView.showError()
                                }
                            }
                        }
                )
    }
}