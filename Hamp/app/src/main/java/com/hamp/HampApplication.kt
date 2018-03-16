package com.hamp

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.hamp.db.RealmMigrations
import com.hamp.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject


class HampApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .name("hamp.realm")
                .schemaVersion(1)
                .migration(RealmMigrations())
                .build()
        Realm.setDefaultConfiguration(configuration)
        Realm.getInstance(configuration)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}