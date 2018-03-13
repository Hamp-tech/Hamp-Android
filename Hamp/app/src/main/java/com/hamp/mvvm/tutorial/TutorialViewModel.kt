package com.hamp.mvvm.tutorial

import android.arch.lifecycle.ViewModel
import com.hamp.preferences.PreferencesManager
import javax.inject.Inject

class TutorialViewModel @Inject constructor(
        private var prefs: PreferencesManager
) : ViewModel() {

    fun setIsFirstTime(isFirstTime: Boolean) {
        prefs.isFirstTime = isFirstTime
    }
}