package com.hamp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils(context: Context) {

    private val PREFS_FILENAME = "com.hamp.prefs"
    private val USERID = "userid"
    private val EMAIL = "email"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var userId: String
        get() = prefs.getString(USERID, "")
        set(value) = prefs.edit().putString(USERID, value).apply()

    var email: String
        get() = prefs.getString(EMAIL, "")
        set(value) = prefs.edit().putString(EMAIL, value).apply()
}