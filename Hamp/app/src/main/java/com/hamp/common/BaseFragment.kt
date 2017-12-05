package com.hamp.common

import android.arch.lifecycle.LifecycleFragment
import com.hamp.mvvm.home.HomeActivity

open class BaseFragment : LifecycleFragment() {

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity)?.currentFragment = this
    }
}