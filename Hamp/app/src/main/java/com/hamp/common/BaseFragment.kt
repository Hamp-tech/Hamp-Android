package com.hamp.common

import android.arch.lifecycle.LifecycleFragment
import android.support.v4.app.Fragment
import com.hamp.ui.home.HomeActivity

open class BaseFragment : LifecycleFragment() {

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity)?.currentFragment = this
    }
}