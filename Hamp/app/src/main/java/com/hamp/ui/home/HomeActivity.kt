package com.hamp.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Service
import com.hamp.extension.*
import com.hamp.ui.home.history.HistoryFragment
import com.hamp.ui.home.profile.ProfileFragment
import com.hamp.ui.home.service.ServiceFragment
import com.hamp.ui.home.service.detail.ServiceDetailFragment
import com.hamp.ui.views.HampNavigationBar
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick

//@BaseActivity.Animation(BaseActivity.FADE)
class HomeActivity : BaseActivity(), HampNavigationBar.HampNavigationBarListener {

    lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initFragments()

        hampNavigationBar.listener = this

        loadServiceFragment()

        back.onClick { supportFragmentManager.popBackStackImmediate() }
    }

    override fun loadFragment(fragmentName: String) {
        when (fragmentName) {
            ServiceFragment::class.java.simpleName -> loadService()
            HistoryFragment::class.java.simpleName -> loadHistory()
            ProfileFragment::class.java.simpleName -> loadProfile()
        }
    }

    private fun loadService() {
        sectionTitle.text = getString(R.string.services)
        video.visibility = View.VISIBLE
        basket.visibility = View.VISIBLE
        back.visibility = View.GONE

        loadServiceFragment()
    }

    private fun loadHistory() {
        sectionTitle.text = getString(R.string.history)
        video.visibility = View.GONE
        basket.visibility = View.GONE
        back.visibility = View.GONE

        loadHistoryFragment()
    }

    private fun loadProfile() {
        sectionTitle.text = getString(R.string.profile)
        video.visibility = View.GONE
        basket.visibility = View.GONE
        back.visibility = View.GONE

        loadProfileFragment()
    }

    fun loadServiceDetail(service: Service) {
        sectionTitle.text = service.name
        video.visibility = View.GONE
        basket.visibility = View.GONE
        back.visibility = View.VISIBLE

        loadServiceDetailFragment(service)
    }

    override fun onBackPressed() {
        when (currentFragment) {
            is ServiceDetailFragment -> {
                supportFragmentManager.popBackStackImmediate()
            }
        }
    }
}