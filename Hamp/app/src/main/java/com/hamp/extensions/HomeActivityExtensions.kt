package com.hamp.extensions

import com.hamp.R
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.home.history.HistoryFragment
import com.hamp.mvvm.home.profile.ProfileFragment
import com.hamp.mvvm.home.service.ServiceFragment

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