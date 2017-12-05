package com.hamp.mvvm.extensions

import android.os.Build
import android.text.Html

fun String.parseHtml(): String {
    return if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}