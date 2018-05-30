package com.hamp.common

import android.os.Bundle
import android.support.annotation.IntDef
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.hamp.R

abstract class BaseActivity : AppCompatActivity() {

    lateinit var currentFragment: Fragment

    companion object {
        const val EXTRA_ANIMATION_TYPE = "EXTRA_ANIMATION_TYPE"
        const val NONE = -1
        const val PUSH = 0
        const val FADE = 1
        const val MODAL = 2
        const val POPUP = 3

        @IntDef(PUSH, FADE, MODAL, POPUP, NONE)

        @Retention(AnnotationRetention.SOURCE)
        annotation class AnimationType

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    @AnimationType
    private var animationType = NONE

    @Retention(AnnotationRetention.RUNTIME)
    annotation class Animation(@AnimationType
                               val value: Int)

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
}

