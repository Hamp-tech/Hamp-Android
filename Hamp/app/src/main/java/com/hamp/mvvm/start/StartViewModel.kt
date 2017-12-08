package com.hamp.mvvm.start

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hamp.mvvm.signup.SignUpViewModel
import com.hamp.repository.UserRepository
import javax.inject.Inject

class StartViewModel @Inject constructor(
        private val repository: UserRepository
) : ViewModel() {

    private val tag = SignUpViewModel::class.java.simpleName

    val state = MutableLiveData<StartAppState>()

    fun checkAppStatus() {
        when {
            repository.isFirstTime() -> state.value = StartAppState.FIRST_TIME
            repository.isUserLogin() -> state.value = StartAppState.LOGIN
            else -> state.value = StartAppState.LOGOUT
        }
    }
}