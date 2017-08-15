package com.hamp.common

import com.hamp.R

interface BaseView {
    fun isActive(): Boolean
    fun showProgress(show: Boolean)
    fun showInternetNotAvailable()
    fun showError(message: Int = R.string.generic_error)
}
