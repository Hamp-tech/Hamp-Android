package com.hamp.mvvm.login

import android.arch.lifecycle.MutableLiveData
import android.util.Patterns
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.common.NetworkViewState
import com.hamp.domain.request.SignInRequest
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.UserRepository
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val repository: UserRepository,
        private var prefs: PreferencesManager
) : BaseViewModel() {

    val validationStatus = MutableLiveData<NetworkViewState>()
    val loginStatus = MutableLiveData<NetworkViewState>()

    fun validateForm(loginEmail: String, loginPassword: String) {
        val errors = arrayListOf<Int>()

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) errors.add(R.string.error_email_empty)
        if (loginPassword.length < 6) errors.add(R.string.error_password_length)

        if (errors.isEmpty()) validationStatus.postValue(NetworkViewState.Success(true))
        else validationStatus.postValue(NetworkViewState.Error(errors))
    }

    fun login(loginEmail: String, loginPassword: String) {
        val signInRequest = SignInRequest(loginEmail, loginPassword)

        repository.signIn(signInRequest)
                .flatMapCompletable {
                    repository.saveUser(it.data)
                            .doOnComplete { it.data.identifier.notNull { prefs.userId = it } }
                }
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { loginStatus.postValue(NetworkViewState.Loading(true)) }
//                .doAfterTerminate { loginStatus.postValue(NetworkViewState.Loading(false)) }
                .subscribeBy(
                        onComplete = {
                            logd("[login.onSuccess]")
                            loginStatus.postValue(NetworkViewState.Success(true))
                        },
                        onError = { e ->
                            loge("[login.onError]" + e.printStackTrace())
                            loginStatus.postValue(NetworkViewState.Loading(false))
                            when (e) {
                                is retrofit2.HttpException -> {
                                    e.response().errorBody()?.let {
                                        loginStatus.postValue(NetworkViewState.Error(
                                                repository.api.convertError(it).message))
                                    }
                                }

                                is UnknownHostException -> loginStatus.postValue(NetworkViewState.Error(
                                        R.string.internet_connection_error))
                                else -> loginStatus.postValue(NetworkViewState.Error(R.string.generic_error))
                            }
                        }
                )
    }
}