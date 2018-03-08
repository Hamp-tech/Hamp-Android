package com.hamp.extensions

//fun HomeActivity.initFragments() {
//    serviceFragment = ServiceFragment.create()
//    historyFragment = HistoryFragment.create()
//    profileFragment = ProfileFragment.create()
//
//    supportFragmentManager.beginTransaction().apply {
//        add(R.id.fragmentContainer, serviceFragment)
//        add(R.id.fragmentContainer, historyFragment)
//        add(R.id.fragmentContainer, profileFragment)
//    }.commit()
//}
//
//fun HomeActivity.loadServiceFragment() {
//    hideKeyboard()
//    supportFragmentManager.beginTransaction().hide(historyFragment).commit()
//    supportFragmentManager.beginTransaction().hide(profileFragment).commit()
//    supportFragmentManager.beginTransaction().show(serviceFragment).commit()
//}
//
//fun HomeActivity.loadHistoryFragment() {
//    hideKeyboard()
//    supportFragmentManager.beginTransaction().hide(serviceFragment).commit()
//    supportFragmentManager.beginTransaction().hide(profileFragment).commit()
//    supportFragmentManager.beginTransaction().show(historyFragment).commit()
//}
//
//fun HomeActivity.loadProfileFragment() {
//    supportFragmentManager.beginTransaction().hide(serviceFragment).commit()
//    supportFragmentManager.beginTransaction().hide(historyFragment).commit()
//    supportFragmentManager.beginTransaction().show(profileFragment).commit()
//}