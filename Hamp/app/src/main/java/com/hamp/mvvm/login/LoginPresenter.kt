package com.hamp.mvvm.login

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.hamp.R
import com.hamp.api
import com.hamp.auth
import com.hamp.prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class LoginPresenter : LoginContract.Presenter {

    private val TAG = LoginPresenter::class.java.simpleName

    lateinit private var mView: LoginContract.View

    override fun setView(view: LoginContract.View) {
        mView = view
    }

    override fun login(email: String, password: String) {
        if (mView.isActive()) mView.showProgress(true)

        auth.loginWithEmailAndPassword(email, password, OnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = task.result.user
                serverLogin(firebaseUser.uid)
            } else {
                if (mView.isActive()) {
                    mView.showProgress(false)
                    when (task.exception) {
                        is FirebaseNetworkException -> mView.showError(R.string.internet_connection_error)
                        is FirebaseAuthInvalidCredentialsException -> mView.loginError(task.exception?.message!!)
                        else -> mView.showError()
                    }
                }
            }
        })
    }

    private fun serverLogin(uid: String) {
        api.getUser(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (mView.isActive()) {
                                mView.showProgress(false)
                                prefs.userId = uid
                                mView.loginSucceed()
                            }
                        },
                        onError = { e ->
                            if (mView.isActive()) {
                                mView.showProgress(false)
                                when (e) {
                                    is retrofit2.HttpException -> {
                                        e.response().errorBody()?.let { mView.loginError(api.convertError(it).message) }
                                    }
                                    is UnknownHostException -> mView.showError(R.string.internet_connection_error)
                                    else -> mView.showError()
                                }
                            }
                        }
                )
    }
}