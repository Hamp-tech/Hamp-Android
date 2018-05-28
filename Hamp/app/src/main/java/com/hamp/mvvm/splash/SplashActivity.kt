package com.hamp.mvvm.splash

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.start.StartActivity
import com.hamp.mvvm.tutorial.TutorialActivity
import com.hamp.preferences.PreferencesManager
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), Injectable {

    @Inject
    lateinit var prefs: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        when {
            prefs.isFirstTime -> startActivity<TutorialActivity>()
            prefs.userId.isNotBlank() -> startActivity<HomeActivity>()
            else -> startActivity<StartActivity>()
        }

        finish()
    }
}