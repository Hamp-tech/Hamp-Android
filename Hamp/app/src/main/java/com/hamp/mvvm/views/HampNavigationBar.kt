package com.hamp.mvvm.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.mvvm.home.history.HistoryFragment
import com.hamp.mvvm.home.profile.ProfileFragment
import com.hamp.mvvm.home.service.ServiceFragment
import kotlinx.android.synthetic.main.home_navigation_bar.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class HampNavigationBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface HampNavigationBarListener {
        fun loadFragment(fragmentName: String)
    }

    lateinit var listener: HampNavigationBarListener

    init {
        LayoutInflater.from(context).inflate(R.layout.home_navigation_bar, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        serviceNavigationButton.onClick { serviceClick() }
        historyNavigationButton.onClick { historyClick() }
        profileNavigationButton.onClick { profileClick() }
    }

    private fun serviceClick() {
        historyNavigationButton.setImageResource(R.drawable.history_off)
        serviceNavigationButton.setImageResource(R.drawable.service_on)
        profileNavigationButton.setImageResource(R.drawable.profile_off)

        listener.loadFragment(ServiceFragment::class.java.simpleName)
    }

    private fun historyClick() {
        historyNavigationButton.setImageResource(R.drawable.history_on)
        serviceNavigationButton.setImageResource(R.drawable.service_off)
        profileNavigationButton.setImageResource(R.drawable.profile_off)

        listener.loadFragment(HistoryFragment::class.java.simpleName)
    }

    private fun profileClick() {
        historyNavigationButton.setImageResource(R.drawable.history_off)
        serviceNavigationButton.setImageResource(R.drawable.service_off)
        profileNavigationButton.setImageResource(R.drawable.profile_on)

        listener.loadFragment(ProfileFragment::class.java.simpleName)
    }
}