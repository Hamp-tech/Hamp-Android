package com.hamp.mvvm.tutorial

import android.arch.lifecycle.ViewModel
import com.hamp.repository.UserRepository
import javax.inject.Inject

class TutorialViewModel @Inject constructor(
        private val repository: UserRepository
) : ViewModel() {

    fun setIsFirstTime(isFirstTime: Boolean) {
        repository.prefs.isFirstTime = isFirstTime
    }

}