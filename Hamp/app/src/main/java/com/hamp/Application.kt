package com.hamp

import android.app.Application
import com.facebook.stetho.Stetho
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class HampApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}