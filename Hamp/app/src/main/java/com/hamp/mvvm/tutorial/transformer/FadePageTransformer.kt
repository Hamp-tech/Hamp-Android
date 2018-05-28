package com.hamp.mvvm.tutorial.transformer

import android.support.v4.view.ViewPager
import android.view.View

class FadePageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = page.width * -position

        if (position <= -1.0F || position >= 1.0F) page.alpha = 0.0F
        else if (position == 0.0F) page.alpha = 1.0F
        else page.alpha = 1.0F - Math.abs(position)
    }
}