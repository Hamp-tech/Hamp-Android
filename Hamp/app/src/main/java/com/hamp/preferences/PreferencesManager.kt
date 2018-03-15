package com.hamp.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class PreferencesManager(context: Context) {

    private val prefsFilename = "com.hamp.prefs"

    private val firstTime = "first_time"
    private val userID = "userid"
    private val phoneSwitch = "phone_switch"
    private val rateHamp = "rate_hamp"
    private val notifications = "notifications"
    private val pickUpTime = "pick_up_time"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var isFirstTime: Boolean
        get() = prefs.getBoolean(firstTime, true)
        set(value) = prefs.edit().putBoolean(firstTime, value).apply()

    var userId: String
        get() = prefs.getString(userID, "")
        set(value) = prefs.edit().putString(userID, value).apply()

    var isPhoneAllowed: Boolean
        get() = prefs.getBoolean(phoneSwitch, true)
        set(value) = prefs.edit().putBoolean(phoneSwitch, value).apply()

    var isRateAllowed: Boolean
        get() = prefs.getBoolean(rateHamp, false)
        set(value) = prefs.edit().putBoolean(rateHamp, value).apply()

    var areNotificationsAllowed: Boolean
        get() = prefs.getBoolean(notifications, true)
        set(value) = prefs.edit().putBoolean(notifications, value).apply()

    var pickUpTurn: String
        get() = prefs.getString(pickUpTime, "M")
        set(value) = prefs.edit().putString(pickUpTime, value).apply()
}