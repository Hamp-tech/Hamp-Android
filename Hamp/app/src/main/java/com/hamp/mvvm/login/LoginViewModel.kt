package com.hamp.mvvm.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Patterns
import com.hamp.R
import com.hamp.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val repository: UserRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

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

//        repository.
    }

    override fun onCleared() = disposables.clear()
}