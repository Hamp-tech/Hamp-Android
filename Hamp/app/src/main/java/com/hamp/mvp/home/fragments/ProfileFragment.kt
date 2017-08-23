package com.hamp.mvp.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.mvp.home.HomeActivity

class ProfileFragment : BaseFragment() {

    companion object {
        fun create() = ProfileFragment().apply {

        }
    }

    val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}