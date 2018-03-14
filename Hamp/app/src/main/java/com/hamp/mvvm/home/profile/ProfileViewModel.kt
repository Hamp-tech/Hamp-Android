package com.hamp.mvvm.home.profile

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.repository.UserRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val repository: UserRepository
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val updateSucceed = MutableLiveData<Boolean>()
    val updateError = MutableLiveData<Any>()

    fun updateUser() {
        loading.value = true


    }
}
