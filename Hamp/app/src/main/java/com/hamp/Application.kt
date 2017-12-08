package com.hamp

import android.app.Activity
import android.app.Application
import com.hamp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

class HampApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}