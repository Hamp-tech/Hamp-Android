package com.hamp.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val PREFS_FILENAME = "com.hamp.prefs"

    private val FIRST_TIME = "first_time"
    private val USERID = "userid"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var isFirstTime: Boolean
        get() = prefs.getBoolean(FIRST_TIME, true)
        set(value) = prefs.edit().putBoolean(FIRST_TIME, value).apply()

    var userId: Long
        get() = prefs.getLong(USERID, -1L)
        set(value) = prefs.edit().putLong(USERID, value).apply()
}