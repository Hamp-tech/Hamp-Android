package com.hamp.common

import android.content.Context
import android.os.Bundle
import android.support.annotation.IntDef
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.hamp.R
import com.hamp.extension.showErrorSnackBar
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ANIMATION_TYPE = "EXTRA_ANIMATION_TYPE"
        const val NONE = -1
        const val PUSH = 0
        const val FADE = 1
        const val MODAL = 2
        const val POPUP = 3

        @IntDef(PUSH.toLong(), FADE.toLong(), MODAL.toLong(), POPUP.toLong(), NONE.toLong())

        @Retention(AnnotationRetention.SOURCE)
        annotation class AnimationType

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    var isRunning: Boolean = false
        private set

    @AnimationType
    private var animationType = NONE

    @Retention(AnnotationRetention.RUNTIME)
    annotation class Animation(@AnimationType
                               val value: Int)

    override fun attachBaseContext(newBase: Context) =
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent != null && intent.extras != null) {
            if (intent.extras.containsKey(EXTRA_ANIMATION_TYPE)) {
                @AnimationType val value = intent.extras.getInt(EXTRA_ANIMATION_TYPE)
                when (value) {
                    FADE, PUSH, MODAL, POPUP -> animationType = value
                    NONE -> {
                    }
                }
            }
        }

        if (animationType == NONE) {
            val annotation = javaClass.getAnnotation(Animation::class.java)
            if (annotation != null) {
                animationType = annotation.value
            }
        }
        animationEnter()
        super.onCreate(savedInstanceState)
        isRunning = true
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {
        super.onStop()
        isRunning = false
    }

    override fun finish() {
        super.finish()
        animationExit()
    }

    private fun animationEnter() {
        when (animationType) {
            FADE -> overridePendingTransition(R.anim.fade_in, R.anim.no_animation)
            PUSH -> overridePendingTransition(R.anim.slide_right_to_left, R.anim.no_animation)
            MODAL -> overridePendingTransition(R.anim.slide_down, R.anim.no_animation)
            POPUP -> overridePendingTransition(R.anim.zoom_in, R.anim.no_animation)
            NONE -> {
            }
        }
    }

    private fun animationExit() {
        when (animationType) {
            FADE -> overridePendingTransition(R.anim.no_animation, R.anim.fade_out)
            PUSH -> overridePendingTransition(R.anim.no_animation, android.R.anim.slide_out_right)
            MODAL -> overridePendingTransition(R.anim.no_animation, R.anim.slide_up)
            POPUP -> overridePendingTransition(R.anim.no_animation, R.anim.zoom_out)
            NONE -> {
            }
        }
    }

    fun isActive() = isRunning

    fun showInternetNotAvailable() =
            showErrorSnackBar(getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG)

    open fun showError(message: Int) = showErrorSnackBar(getString(message), Snackbar.LENGTH_LONG)
}

