package com.hamp.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.View
import com.hamp.mvvm.home.HomeActivity

open class BaseFragment : Fragment() {

    class ViewLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): LifecycleRegistry {
            return lifecycleRegistry
        }
    }

    @Nullable
    var viewLifecycleOwner: ViewLifecycleOwner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner = ViewLifecycleOwner()
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity)?.currentFragment = this

        if (viewLifecycleOwner != null) {
            viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    override fun onPause() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        viewLifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewLifecycleOwner = null
        super.onDestroyView()
    }
}