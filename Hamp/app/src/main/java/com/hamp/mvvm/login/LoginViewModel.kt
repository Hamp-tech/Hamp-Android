package com.hamp.mvvm.login

import android.arch.lifecycle.MutableLiveData
import android.util.Patterns
import com.hamp.R
import com.hamp.api.exception.ServerException
import com.hamp.common.BaseViewModel
import com.hamp.common.NetworkViewState
import com.hamp.domain.request.SignInRequest
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val repository: UserRepository,
        private val prefs: PreferencesManager
) : BaseViewModel() {

    val validationStatus = MutableLiveData<NetworkViewState>()
    val loginStatus = MutableLiveData<NetworkViewState>()

    fun validateForm(loginEmail: String, loginPassword: String) {
        val errors = arrayListOf<Int>()

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) errors.add(R.string.error_email_empty)
        if (loginPassword.length < 6) errors.add(R.string.error_password_length)

        if (errors.isEmpty()) validationStatus.value = NetworkViewState.Success(true)
        else validationStatus.value = NetworkViewState.Error(errors)
    }

    fun login(loginEmail: String, loginPassword: String) {
        val signInRequest = SignInRequest(loginEmail, loginPassword)

        disposables += repository.signIn(signInRequest)
                .flatMapCompletable {
                    repository.saveUser(it.data)
                            .doOnComplete { it.data.identifier.notNull { prefs.userId = it } }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loginStatus.value = NetworkViewState.Loading() }
                .subscribeBy(
                        onComplete = {
                            logd("[login.onSuccess]")
                            loginStatus.value = NetworkViewState.Success(true)
                        },
                        onError = { e ->
                            loge("[login.onError]" + e.printStackTrace())
                            when (e) {
                                is ServerException -> e.message.notNull { NetworkViewState.Error(it) }
                                is IOException -> loginStatus.value = NetworkViewState.Error(
                                        R.string.internet_connection_error)
                                else -> loginStatus.value = NetworkViewState.Error(
                                        R.string.generic_error)
                            }
                        }
                )
    }
}