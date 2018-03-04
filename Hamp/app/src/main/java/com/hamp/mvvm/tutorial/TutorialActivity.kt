package com.hamp.mvvm.tutorial

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.mvvm.start.StartActivity
import com.hamp.mvvm.tutorial.adapter.TutorialAdapter
import com.hamp.mvvm.tutorial.transformer.FadePageTransformer
import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

class TutorialActivity : BaseActivity(), Injectable, ViewPager.OnPageChangeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var tutorialViewModel: TutorialViewModel

    private val rotation = 120f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        setUpViewModel()

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

    private fun setUpViewModel() {
        tutorialViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TutorialViewModel::class.java)
    }

    private fun goToStartActivity() {
        tutorialViewModel.setIsFirstTime(false)
        startActivity(Intent(this, StartActivity::class.java))
        finish()
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        when (position) {
            0 -> circleTutorial.rotation = positionOffset * rotation
            1 -> circleTutorial.rotation = (position + positionOffset) * rotation
            2 -> circleTutorial.rotation = (position + positionOffset) * rotation
            else -> circleTutorial.rotation = (position + positionOffset) * rotation
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