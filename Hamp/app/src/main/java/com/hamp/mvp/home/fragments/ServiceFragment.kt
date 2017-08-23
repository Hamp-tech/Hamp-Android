package com.hamp.mvp.home.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.mvp.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_service.*

class ServiceFragment : BaseFragment() {

    companion object {
        fun create() = ServiceFragment().apply {

        }
    }

    val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_service, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvServices.layoutManager = GridLayoutManager(context, 2)
//        rvServices.adapter = ServicesAdapter(this, Planogram().getAll().toMutableList(), this)
    }
}