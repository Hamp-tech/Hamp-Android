package com.hamp.common

interface BasePresenter<in T : BaseView> {
    fun setView(view: T)
}
