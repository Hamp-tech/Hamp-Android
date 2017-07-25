package com.hamp

import android.app.Application
import com.facebook.stetho.Stetho

class HampApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

//        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/FuturaStd-Medium.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build())
    }
}