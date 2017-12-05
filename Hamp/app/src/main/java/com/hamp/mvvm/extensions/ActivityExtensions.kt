package com.hamp.mvvm.extensions

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.hamp.R
import org.jetbrains.anko.contentView

fun Activity.showErrorSnackBar(message: String, duration: Int) {
    hideKeyboard()

    val snack = contentView?.let { Snackbar.make(it, message, duration) }
    snack?.setActionTextColor(ContextCompat.getColor(this, R.color.white))

    val view = snack?.view
    view?.setBackgroundColor(ContextCompat.getColor(this, R.color.red_error))
    snack?.show()
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}