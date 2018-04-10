package com.hamp.mvvm.home.profile

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.db.domain.User
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.ServiceRepository
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val serviceRepository: ServiceRepository,
        private var prefs: PreferencesManager
) : BaseViewModel() {


    val isPhoneAllowed = prefs.isPhoneAllowed
    val isRateAllowed = prefs.isRateAllowed
    val areNotificationsAllowed = prefs.areNotificationsAllowed
    val pickUpTurn = prefs.pickUpTurn

    val user = MutableLiveData<User>()
    val loading = MutableLiveData<Boolean>()
    val updateSucceed = MutableLiveData<Boolean>()
    val updateError = MutableLiveData<Any>()

    init {
        userRepository.getDBUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribeBy(
                        onSuccess = { user.value = it },
                        onError = { }
                )
    }

    fun updateUser(user: User) {
        disposables.add(userRepository.updateUser(user, prefs.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribeBy(
                        onSuccess = {
                            logd("[updateUser.onSuccess]")
                            userRepository.saveUser(it.data).subscribeOn(Schedulers.io())
                            updateSucceed.value = true
                        },
                        onError = { e ->
                            loge("[updateUser.onError]" + e.printStackTrace())

                            when (e) {
                                is retrofit2.HttpException -> {
                                    e.response().errorBody()?.let {
                                        updateError.value = userRepository.api.convertError(it).message
                                    }
                                }
                                is UnknownHostException -> updateError.value = R.string.internet_connection_error
                                else -> updateError.value = R.string.generic_error
                            }
                        }
                ))
    }

    fun savePreferences(isPhoneAllowed: Boolean, isRateAllowed: Boolean,
                        areNotificationsAllowed: Boolean, pickUpTurn: String) {
        prefs.isPhoneAllowed = isPhoneAllowed
        prefs.isRateAllowed = isRateAllowed
        prefs.areNotificationsAllowed = areNotificationsAllowed
        prefs.pickUpTurn = pickUpTurn
    }

    fun logout() {
        prefs.userId = ""
        userRepository.deleteUser().subscribeOn(Schedulers.io())
        serviceRepository.deleteBasket().subscribeOn(Schedulers.io())
    }
}