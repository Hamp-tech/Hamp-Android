package com.hamp.mvvm.start

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.preferences.PreferencesManager
import javax.inject.Inject

class StartViewModel @Inject constructor(
        private var prefs: PreferencesManager
) : BaseViewModel() {

    val state = MutableLiveData<StartAppState>()

    fun checkAppStatus() {
        when {
            prefs.isFirstTime -> state.value = StartAppState.FIRST_TIME
            prefs.userId.isNotBlank() -> state.value = StartAppState.LOGIN
            else -> state.value = StartAppState.LOGOUT
        }
    }
}