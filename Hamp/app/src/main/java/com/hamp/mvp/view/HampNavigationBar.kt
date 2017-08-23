package com.hamp.mvp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.extension.loadImg
import com.hamp.mvp.home.fragments.HistoryFragment
import com.hamp.mvp.home.fragments.ProfileFragment
import com.hamp.mvp.home.fragments.ServiceFragment
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

        historyNavigationButton.onClick { historyClick() }
        serviceNavigationButton.onClick { serviceClick() }
        profileNavigationButton.onClick { profileClick() }
    }

    private fun historyClick() {
        historyNavigationButton.loadImg(R.drawable.history_on)
        serviceNavigationButton.loadImg(R.drawable.service_off)
        profileNavigationButton.loadImg(R.drawable.profile_off)

        listener.loadFragment(HistoryFragment::class.java.simpleName)
    }

    private fun profileClick() {
        historyNavigationButton.loadImg(R.drawable.history_off)
        serviceNavigationButton.loadImg(R.drawable.service_off)
        profileNavigationButton.loadImg(R.drawable.profile_on)

        listener.loadFragment(ProfileFragment::class.java.simpleName)
    }

    private fun serviceClick() {
        historyNavigationButton.loadImg(R.drawable.history_off)
        serviceNavigationButton.loadImg(R.drawable.service_on)
        profileNavigationButton.loadImg(R.drawable.profile_off)

        listener.loadFragment(ServiceFragment::class.java.simpleName)
    }
}