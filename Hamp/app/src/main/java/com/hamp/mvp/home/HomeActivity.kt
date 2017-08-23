package com.hamp.mvp.home

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.loadHistoryFragment
import com.hamp.extension.loadProfileFragment
import com.hamp.extension.loadServiceFragment
import com.hamp.mvp.home.fragments.HistoryFragment
import com.hamp.mvp.home.fragments.ProfileFragment
import com.hamp.mvp.home.fragments.ServiceFragment
import com.hamp.mvp.view.HampNavigationBar
import kotlinx.android.synthetic.main.activity_home.*

@BaseActivity.Animation(BaseActivity.FADE)
class HomeActivity : BaseActivity(), HampNavigationBar.HampNavigationBarListener {

    lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        hampNavigationBar.listener = this

        sectionTitle.text = getString(R.string.services)

        loadServiceFragment()
    }

    override fun loadFragment(fragmentName: String) {
        when (fragmentName) {
            HistoryFragment::class.java.simpleName -> loadHistoryFragment()
            ServiceFragment::class.java.simpleName -> loadServiceFragment()
            ProfileFragment::class.java.simpleName -> loadProfileFragment()
        }
    }
}
