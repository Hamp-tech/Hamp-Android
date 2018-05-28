package com.hamp.mvvm.tutorial.adapter

import android.content.res.TypedArray
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.hamp.mvvm.tutorial.fragment.TutorialFragment

class TutorialAdapter(fm: FragmentManager, private val images: TypedArray,
                      private val titles: Array<String>,
                      private val instructions: Array<String>) : FragmentStatePagerAdapter(fm) {

    private val pagesNumber = 4

    override fun getItem(position: Int): Fragment = TutorialFragment.create(
            images.getResourceId(position, 0),
            titles[position],
            instructions[position])

    override fun getCount() = pagesNumber
}