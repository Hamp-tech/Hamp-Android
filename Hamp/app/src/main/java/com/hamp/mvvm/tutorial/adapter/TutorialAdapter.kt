package com.hamp.mvvm.tutorial.adapter

import android.content.res.TypedArray
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hamp.mvvm.tutorial.fragment.TutorialFragment

class TutorialAdapter(fm: FragmentManager, private val images: TypedArray,
                      private val titles: Array<String>,
                      private val instructions: Array<String>) : FragmentPagerAdapter(fm) {

    private val NUM_INSTRUCTIONS = 4

    override fun getItem(position: Int): Fragment =
            TutorialFragment.create(images.getResourceId(position, 0), titles[position], instructions[position])

    override fun getCount() = NUM_INSTRUCTIONS
}