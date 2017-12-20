package com.hamp.mvvm.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Patterns
import com.google.firebase.iid.FirebaseInstanceId
import com.hamp.R
import com.hamp.extensions.logd
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
        private val repository: UserRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val validationErrors = MutableLiveData<List<Int>>()
    val validationSucceeded = MutableLiveData<Boolean>()

    val signUpError = MutableLiveData<Any>()
    val signUpSucceed = MutableLiveData<Boolean>()

    fun validateForm(signUpName: String, signUpSurname: String, signUpEmail: String,
                     signUpPhone: String, signUpBday: String, signUpPassword: String,
                     signUpConfirmPassword: String) {
        val errors = arrayListOf<Int>()

        if (signUpName.isEmpty()) errors.add(R.string.error_name_empty)
        if (signUpSurname.isEmpty()) errors.add(R.string.error_surname_empty)
        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()) errors.add(R.string.error_email_empty)
        if (signUpPhone.length < 9) errors.add(R.string.error_phone_empty)
        if (signUpBday.isEmpty()) errors.add(R.string.error_birthday_empty)
        if (signUpPassword.length < 6) errors.add(R.string.error_password_length)
        if (signUpConfirmPassword == signUpPassword) errors.add(R.string.error_confirm_password)

        if (errors.isEmpty()) {
            validationSucceeded.value = true
            validationErrors.value = emptyList()
        } else {
            validationErrors.value = errors
            validationSucceeded.value = false
        }
    }

    fun signUp(email: String, password: String, name: String, surname: String, phone: String,
               birthday: String, gender: Int) {
        loading.value = true

        val tokenFCM = FirebaseInstanceId.getInstance().token ?: ""
        disposables.add(repository.signUp(email, password, name, surname, phone, birthday, gender, tokenFCM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("[signUp.onSuccess]")
                            loading.value = false
                            repository.saveUser(it.data)
                            signUpSucceed.value = true
                        },
                        onError = { e ->
                            logd("[signUp.onError]" + e.printStackTrace())
                            loading.value = false

                            when (e) {
                                is retrofit2.HttpException -> {
                                    e.response().errorBody()?.let {
                                        signUpError.value = repository.api.convertError(it).message
                                    }
                                }
                                is UnknownHostException -> signUpError.value = R.string.internet_connection_error
                                else -> signUpError.value = R.string.generic_error
                            }
                        }
                ))
    }

    override fun onCleared() = disposables.clear()
}