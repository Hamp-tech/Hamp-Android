package com.hamp.mvvm.tutorial

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.prefs
import com.hamp.mvvm.start.StartActivity
import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TutorialActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    val ROTATION = 120f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val titles = resources.getStringArray(R.array.title_instructions)
        val instructions = resources.getStringArray(R.array.instructions)
        val images = resources.obtainTypedArray(R.array.images_instructions)

        val tutorialAdapter = TutorialAdapter(supportFragmentManager, images, titles, instructions)
        tutorialPager.setPageTransformer(false, FadePageTransformer())
        tutorialPager.adapter = tutorialAdapter
        tutorialPager.addOnPageChangeListener(this)
        indicator.setViewPager(tutorialPager)

        startButton.onClick { goToStartActivity() }
    }

    private fun goToStartActivity() {
        prefs.isFirstTime = false
        startActivity(Intent(this, StartActivity::class.java))
        finish()
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        when (position) {
            0 -> circleTutorial.rotation = positionOffset * ROTATION
            1 -> circleTutorial.rotation = (position + positionOffset) * ROTATION
            2 -> circleTutorial.rotation = (position + positionOffset) * ROTATION
            else -> circleTutorial.rotation = (position + positionOffset) * ROTATION
        }
    }

    override fun onPageSelected(position: Int) {
        if (position == 3) {
            startButton.isEnabled = true
            startButton.animate().alpha(1f)
        } else {
            startButton.animate().alpha(0f)
            startButton.isEnabled = false
        }
    }
}