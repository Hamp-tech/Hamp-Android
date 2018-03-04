package com.hamp.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class PreferencesManager(context: Context) {

    private val PREFS_FILENAME = "com.hamp.prefs"

    private val FIRST_TIME = "first_time"
    private val USERID = "userid"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var isFirstTime: Boolean
        get() = prefs.getBoolean(FIRST_TIME, true)
        set(value) = prefs.edit().putBoolean(FIRST_TIME, value).apply()

    var userId: String
        get() = prefs.getString(USERID, "")
        set(value) = prefs.edit().putString(USERID, value).apply()
}