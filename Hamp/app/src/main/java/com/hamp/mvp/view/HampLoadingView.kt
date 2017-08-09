package com.hamp.mvp.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.hamp.R

class HampLoadingView(val context: Context) {

    var dialog = Dialog(context, R.style.LoadingDialogStyle)

    @JvmOverloads fun show(cancelable: Boolean = false,
                           cancelListener: DialogInterface.OnCancelListener? = null): Dialog {
        val inflator = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.loading_view, null)

        view.layoutParams = ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)

        dialog = Dialog(context, R.style.LoadingDialogStyle)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.setCancelable(cancelable)
        dialog.setOnCancelListener(cancelListener)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window.statusBarColor = ContextCompat.getColor(context, R.color.white)

        dialog.show()

        return dialog
    }
}