package com.hamp.extensions

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.hamp.R
import org.jetbrains.anko.contentView

fun Activity.showErrorSnackBar(message: Any, duration: Int) {
    hideKeyboard()

    val messageText = message as? String ?: if (message is Int) getString(message)
    else getString(R.string.generic_error)

    val snack = contentView?.let { Snackbar.make(it, messageText, duration) }
    snack?.setActionTextColor(ContextCompat.getColor(this, R.color.white))

    val view = snack?.view
    view?.setBackgroundColor(ContextCompat.getColor(this, R.color.red_error))
    snack?.show()
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}

//inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
//    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
//}