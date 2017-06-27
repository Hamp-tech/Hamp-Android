package com.galodb.hamp.common

interface BasePresenter<in T : BaseView> {
    fun setView(view: T)
}
