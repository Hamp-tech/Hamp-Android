package com.hamp.mvvm.login

import android.arch.lifecycle.MutableLiveData
import android.util.Patterns
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.domain.request.SignInRequest
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val repository: UserRepository,
        private var prefs: PreferencesManager
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val validationErrors = MutableLiveData<List<Int>>()
    val validationSucceeded = MutableLiveData<Boolean>()
    val loginError = MutableLiveData<Any>()
    val loginSucceed = MutableLiveData<Boolean>()

    fun validateForm(loginEmail: String, loginPassword: String) {
        val errors = arrayListOf<Int>()

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) errors.add(R.string.error_email_empty)
        if (loginPassword.length < 6) errors.add(R.string.error_password_length)

        if (errors.isEmpty()) {
            validationSucceeded.value = true
            validationErrors.value = emptyList()
        } else {
            validationErrors.value = errors
            validationSucceeded.value = false
        }
    }

    fun login(loginEmail: String, loginPassword: String) {
        loading.value = true

        val signInRequest = SignInRequest(loginEmail, loginPassword)

        disposables.add(repository.signIn(signInRequest)
                .flatMapCompletable {
                    repository.saveUser(it.data)
                            .doOnComplete { it.data.identifier.notNull { prefs.userId = it } }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            logd("[login.onSuccess]")

                            loading.value = false
                            loginSucceed.value = true
                        },
                        onError = { e ->
                            loge("[login.onError]" + e.printStackTrace())
                            loading.value = false

                            when (e) {
                                is retrofit2.HttpException -> {
                                    e.response().errorBody()?.let {
                                        loginError.value = repository.api.convertError(it).message
                                    }
                                }
                                is UnknownHostException -> loginError.value = R.string.internet_connection_error
                                else -> loginError.value = R.string.generic_error
                            }
                        }
                ))
    }
}