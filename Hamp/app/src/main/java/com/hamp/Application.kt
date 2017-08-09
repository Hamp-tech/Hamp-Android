package com.hamp

import android.app.Application
import com.facebook.stetho.Stetho
import com.hamp.api.RestApi
import com.hamp.utils.PreferencesUtils
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

val api: RestApi by lazy {
    HampApplication.hampApi!!
}

val prefs: PreferencesUtils by lazy {
    HampApplication.preferences!!
}

class HampApplication : Application() {

    companion object {
        var hampApi: RestApi? = null
        var preferences: PreferencesUtils? = null
    }

    override fun onCreate() {
        super.onCreate()

        hampApi = RestApi()
        preferences = PreferencesUtils(this)

        Stetho.initializeWithDefaults(this)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}