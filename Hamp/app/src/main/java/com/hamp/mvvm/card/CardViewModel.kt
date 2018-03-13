package com.hamp.mvvm.card

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.domain.Card
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.preferences.PreferencesManager
import com.hamp.repository.CardRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class CardViewModel @Inject constructor(
        private val repository: CardRepository,
        private var prefs: PreferencesManager
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val addCardSucceed = MutableLiveData<Boolean>()
    val addCardError = MutableLiveData<Any>()

    fun addCard(cardNumber: String, cardExpiryDate: String, cardCvv: String, cardHolder: String) {
        loading.value = true

        val date = cardExpiryDate.split("/").map { it.toInt() }
        val card = Card(cardNumber, date[0], date[1], cardCvv)

        disposables.add(repository.addCard(card, prefs.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("[addCard.onSuccess]")
                            loading.value = false
                            addCardSucceed.value = true
                        },
                        onError = { e ->
                            loge("[addCard.onError]" + e.printStackTrace())
                            loading.value = false

                            when (e) {
                                is retrofit2.HttpException -> {
                                    e.response().errorBody()?.let {
                                        addCardError.value = repository.api.convertError(it).message
                                    }
                                }
                                is UnknownHostException -> addCardError.value = R.string.internet_connection_error
                                else -> addCardError.value = R.string.generic_error
                            }
                        }
                )
        )
    }
}