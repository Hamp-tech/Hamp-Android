package com.galodb.hamp.common

interface BaseView {
    fun isActive(): Boolean
    fun showProgress(show: Boolean)
    fun showInternetNotAvaiable()
}
