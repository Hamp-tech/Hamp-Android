package com.hamp.mvvm.home

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extensions.hideKeyboard
import com.hamp.mvvm.home.history.HistoryFragment
import com.hamp.mvvm.home.profile.ProfileFragment
import com.hamp.mvvm.home.service.ServiceFragment
import com.hamp.mvvm.views.HampNavigationBar
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick

//@BaseActivity.Animation(BaseActivity.FADE)
class HomeActivity : BaseActivity(), HampNavigationBar.HampNavigationBarListener {

//    lateinit var serviceFragment: ServiceFragment
//    lateinit var historyFragment: HistoryFragment
//    lateinit var profileFragment: ProfileFragment
//
//    private var isBasketEmpty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        initFragments()

        hampNavigationBar.listener = this

        loadService()

        basket.onClick { goToBasket() }
        profileEditSave.onClick { profileEditMode() }
    }

    override fun loadFragment(fragmentName: String) {
        when (fragmentName) {
            ServiceFragment::class.java.simpleName -> loadService()
            HistoryFragment::class.java.simpleName -> loadHistory()
            ProfileFragment::class.java.simpleName -> loadProfile()
        }
    }

    private fun loadService() {
//        sectionTitle.text = getString(R.string.services)
//
//        profileEditSave.visibility = View.GONE
//        if (isBasketEmpty) {
//            basketCounterContainer.visibility = View.GONE
//            basketCounter.visibility = View.GONE
//        } else {
//            basketCounterContainer.visibility = View.VISIBLE
//            basketCounter.visibility = View.VISIBLE
//        }
//
//        basket.visibility = View.VISIBLE
//        video.visibility = View.VISIBLE
//
//        loadServiceFragment()
    }

    private fun loadHistory() {
//        sectionTitle.text = getString(R.string.history)
//        video.visibility = View.GONE
//        basket.visibility = View.GONE
//        profileEditSave.visibility = View.GONE
//
//        basketCounterContainer.visibility = View.GONE
//        basketCounter.visibility = View.GONE
//
//        loadHistoryFragment()
    }

    private fun loadProfile() {
//        sectionTitle.text = getString(R.string.profile)
//        video.visibility = View.GONE
//        basket.visibility = View.GONE
//
//        basketCounterContainer.visibility = View.GONE
//        basketCounter.visibility = View.GONE
//
//        profileEditSave.visibility = View.VISIBLE
//
//        loadProfileFragment()
    }

    fun modifyBasketCounter(basketItems: Int) {
//        if (basketItems == 0) {
//            isBasketEmpty = true
//            basket.setImageResource(R.drawable.empty_basket)
//            basketCounterContainer.visibility = View.GONE
//            basketCounter.visibility = View.GONE
//        } else {
//            isBasketEmpty = false
//            basket.setImageResource(R.drawable.basket)
//            basketCounterContainer.visibility = View.VISIBLE
//            basketCounter.visibility = View.VISIBLE
//            basketCounter.text = basketItems.toString()
//        }
    }

    private fun profileEditMode() = (currentFragment as? ProfileFragment)?.editionMode()

    fun editModeSwitch(editMode: Boolean) {
        if (editMode) profileEditSave.text = getString(R.string.profile_save)
        else {
            profileEditSave.text = getString(R.string.profile_edit)
            hideKeyboard()
        }
    }

    private fun goToBasket() {
//        if (!isBasketEmpty) startActivity<BasketActivity>()
    }
}