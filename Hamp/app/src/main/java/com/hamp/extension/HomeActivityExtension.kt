package com.hamp.extension

import com.hamp.R
import com.hamp.domain.Service
import com.hamp.ui.home.HomeActivity
import com.hamp.ui.home.history.HistoryFragment
import com.hamp.ui.home.profile.ProfileFragment
import com.hamp.ui.home.service.ServiceFragment
import com.hamp.ui.home.service.detail.ServiceDetailFragment

val serviceFragment = ServiceFragment.create()
val historyFragment = HistoryFragment.create()
val profileFragment = ProfileFragment.create()

fun HomeActivity.initFragments() {
    supportFragmentManager.beginTransaction().apply {
        add(R.id.fragmentContainer, serviceFragment)
        add(R.id.fragmentContainer, historyFragment)
        add(R.id.fragmentContainer, profileFragment)
    }.commit()
}

fun HomeActivity.loadServiceFragment() {
    supportFragmentManager.beginTransaction().show(serviceFragment).commit()
    supportFragmentManager.beginTransaction().hide(historyFragment).commit()
    supportFragmentManager.beginTransaction().hide(profileFragment).commit()
}

fun HomeActivity.loadHistoryFragment() {
    supportFragmentManager.beginTransaction().hide(serviceFragment).commit()
    supportFragmentManager.beginTransaction().show(historyFragment).commit()
    supportFragmentManager.beginTransaction().hide(profileFragment).commit()
}

fun HomeActivity.loadProfileFragment() {
    supportFragmentManager.beginTransaction().hide(serviceFragment).commit()
    supportFragmentManager.beginTransaction().hide(historyFragment).commit()
    supportFragmentManager.beginTransaction().show(profileFragment).commit()
}

fun HomeActivity.loadServiceDetailFragment(service: Service) {
    supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.slide_right_to_left, android.R.anim.slide_out_right)
        replace(R.id.fragmentContainer, ServiceDetailFragment.create(service))
        addToBackStack(ServiceDetailFragment::class.java.simpleName)
    }.setCustomAnimations(R.anim.slide_right_to_left, android.R.anim.slide_out_right).commit()
}