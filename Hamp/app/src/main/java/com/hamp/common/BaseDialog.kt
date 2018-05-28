package com.hamp.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.View

abstract class BaseDialog {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    // Required bools
    open var cancelable: Boolean = true
    open var isBackGroundTransparent: Boolean = true

    // Dialog
    open var dialog: AlertDialog? = null

    // Dialog create
    open fun create(): AlertDialog {
        dialog = builder
                .setCancelable(cancelable)
                .create()

        // Very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    // Cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
            builder.setOnCancelListener { func() }
}