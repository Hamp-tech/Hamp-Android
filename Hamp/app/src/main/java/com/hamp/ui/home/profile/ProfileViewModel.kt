package com.hamp.ui.home.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hamp.domain.User
import com.hamp.firebase.CallbackType
import com.hamp.firebase.FirebaseDatabaseRepositoryCallback
import com.hamp.repository.UserRepository

class ProfileViewModel : ViewModel() {

    private var user: MutableLiveData<User>? = null
    private val repository = UserRepository()

    fun getUser(): LiveData<User> {
        if (user == null) {
            user = MutableLiveData()
            loadUser()
        }

        return user as MutableLiveData<User>
    }

    private fun loadUser() {
        repository.addListener(object : FirebaseDatabaseRepositoryCallback<User>(CallbackType.OBJECT) {
            override fun onSuccess(result: User) {
                user?.setValue(result)
            }

            override fun onError(e: Exception) {
                user?.setValue(null)
            }
        })
    }

    override fun onCleared() {
        repository.removeListener()
    }
}
