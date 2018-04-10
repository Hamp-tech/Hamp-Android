package com.hamp.mvvm.paymentMethod

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.domain.Book
import com.hamp.domain.Card
import com.hamp.domain.Point
import com.hamp.domain.Transaction
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.ServiceRepository
import com.hamp.repository.TransactionRepository
import com.hamp.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class PaymentMethodViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val serviceRepository: ServiceRepository,
        private val transactionRepository: TransactionRepository,
        private var prefs: PreferencesManager
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val cards = MutableLiveData<List<Card>>()
    val transactionSucceeded = MutableLiveData<Boolean>()
    val transactionError = MutableLiveData<Any>()

    var currentCard: Card? = null
    var totalPrice: Double = 0.0

    fun getCards() {
        userRepository.getDBUser()
                .flatMap { userRepository.updateUser(it, prefs.userId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doOnSubscribe { loading.value = false }
                .subscribeBy(
                        onSuccess = { cards.value = it.data.cards },
                        onError = { }
                )
    }

    fun finalizePurchase() {
        val pickUpTime: String = if (prefs.pickUpTurn == "M") "1" else "2"
        currentCard.notNull { card ->
            serviceRepository.getServicesQuantity()
                    .flatMap {
                        val transaction = Transaction(creditCard = card, booking = Book(
                                it,
                                totalPrice,
                                Point(),
                                pickUpTime))

                        transactionRepository.createTransaction(transaction, prefs.userId)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loading.value = true }
                    .doAfterTerminate { loading.value = false }
                    .subscribeBy(
                            onSuccess = {
                                logd("[finalizePurchase.onSuccess]")
                                transactionSucceeded.value = true
                            },
                            onError = { e ->
                                loge("[finalizePurchase.onError]" + e.printStackTrace())
                                when (e) {
                                    is retrofit2.HttpException -> {
                                        e.response().errorBody()?.let {
                                            transactionError.value = userRepository.api.convertError(it).message
                                        }
                                    }
                                    is UnknownHostException -> transactionError.value = R.string.internet_connection_error
                                    else -> transactionError.value = R.string.generic_error
                                }
                            })
        }
    }
}