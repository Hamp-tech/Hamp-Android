package com.hamp.common

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    var disposables = CompositeDisposable()

    override fun onCleared() = disposables.clear()

}