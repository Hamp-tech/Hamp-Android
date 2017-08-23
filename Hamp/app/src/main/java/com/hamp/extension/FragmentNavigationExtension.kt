package com.hamp.extension

import com.hamp.R
import com.hamp.mvp.home.HomeActivity
import com.hamp.mvp.home.fragments.HistoryFragment
import com.hamp.mvp.home.fragments.ProfileFragment
import com.hamp.mvp.home.fragments.ServiceFragment

fun HomeActivity.loadServiceFragment() {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainer, ServiceFragment.create())
//        addToBackStack(ServiceFragment::class.java.simpleName)
    }.commit()
}

fun HomeActivity.loadHistoryFragment() {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainer, HistoryFragment.create())
//        addToBackStack(HistoryFragment::class.java.simpleName)
    }.commit()
}

fun HomeActivity.loadProfileFragment() {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainer, ProfileFragment.create())
//        addToBackStack(ProfileFragment::class.java.simpleName)
    }.commit()
}