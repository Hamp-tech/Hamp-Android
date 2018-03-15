package com.hamp.extensions

import android.arch.lifecycle.*
import android.support.v4.app.Fragment

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) =
        observe(owner, Observer<T> { v -> observer.invoke(v) })

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <reified T : ViewModel> getViewModel(activity: Fragment,
                                                viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(activity, viewModelFactory)[T::class.java]
}