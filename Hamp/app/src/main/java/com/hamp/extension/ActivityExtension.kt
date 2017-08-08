package com.hamp.extension

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.hamp.R
import org.jetbrains.anko.contentView

fun Activity.showErrorSnackbar(message: String, duration: Int) {
    val snack = Snackbar.make(contentView!!, message, duration)
    snack.setActionTextColor(R.color.white)

    val view = snack.view
    view.setBackgroundColor(ContextCompat.getColor(this, R.color.red_error))
    snack.show()
}

//fun Activity.showErrorSnackbar(message: String, duration: Int) {
//    var snack = Snackbar.make(contentView!!, message, duration)
//    var view = snack.view
//    view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackBarBackgroundColor))
//    snack.show()
//}
//
//fun Activity.showErrorSnackbar(message: Any, duration: Int) {
//    var snack: Snackbar
//    if (message is String) snack = Snackbar.make(contentView as View, message, duration)
//    else snack = Snackbar.make(contentView as View, resources.getString(message as Int), duration)
//
//    var view = snack.view
//    view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackBarBackgroundColor))
//    snack.show()
//}
//
//fun Activity.showErrorSnackbarRetry(message: String,
//                                    actionMessage: String, function: View.OnClickListener) {
//    var snack = Snackbar.make(contentView!!, message, Snackbar.LENGTH_INDEFINITE)
//    var view = snack.view
//    view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackBarBackgroundColor))
//    snack.setAction(actionMessage, function)
//    snack.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
//    snack.show()
//}
