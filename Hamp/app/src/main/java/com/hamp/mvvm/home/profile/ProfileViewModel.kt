package com.hamp.mvvm.home.profile

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.data.api.exception.ServerException
import com.hamp.common.BaseViewModel
import com.hamp.common.NetworkViewState
import com.hamp.data.db.domain.User
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.data.preferences.PreferencesManager
import com.hamp.repository.ServiceRepository
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val serviceRepository: ServiceRepository,
        private val prefs: PreferencesManager
) : BaseViewModel() {

    lateinit var user: User

    val userStatus = MutableLiveData<NetworkViewState>()
    val updateStatus = MutableLiveData<NetworkViewState>()

    init {
        disposables += userRepository.getDBUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { userStatus.value = NetworkViewState.Loading() }
                .subscribeBy(
                        onSuccess = {
                            user = it
                            userStatus.value = NetworkViewState.Success(it)
                        },
                        onError = { userStatus.value = NetworkViewState.Error(R.string.generic_error) }
                )
    }

    fun updateUser(user: User) {
        disposables += userRepository.updateUser(user, prefs.userId)
                .flatMapCompletable { userRepository.saveUser(it.data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { updateStatus.value = NetworkViewState.Loading() }
                .subscribeBy(
                        onComplete = {
                            logd("[updateUser.onSuccess]")
                            updateStatus.value = NetworkViewState.Success(true)
                        },
                        onError = { e ->
                            loge("[updateUser.onError]" + e.printStackTrace())

                            when (e) {
                                is ServerException -> e.message.notNull {
                                    updateStatus.value = NetworkViewState.Error(it)
                                }
                                is IOException -> updateStatus.value = NetworkViewState.Error(
                                        R.string.internet_connection_error)
                                else -> updateStatus.value = NetworkViewState.Error(
                                        R.string.generic_error)
                            }
                        }
                )
    }

    fun isPhoneAllowed() = prefs.isPhoneAllowed
    fun isRateAllowed() = prefs.isRateAllowed
    fun areNotificationsAllowed() = prefs.areNotificationsAllowed
    fun pickUpTurn() = prefs.pickUpTurn

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