package com.hamp.mvvm.card

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.data.api.exception.ServerException
import com.hamp.common.BaseViewModel
import com.hamp.common.NetworkViewState
import com.hamp.domain.Card
import com.hamp.extensions.logd
import com.hamp.extensions.loge
import com.hamp.extensions.notNull
import com.hamp.data.preferences.PreferencesManager
import com.hamp.repository.CardRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class CardViewModel @Inject constructor(
        private val repository: CardRepository,
        private val prefs: PreferencesManager
) : BaseViewModel() {

    val cardStatus = MutableLiveData<NetworkViewState>()

    fun addCard(cardNumber: String, cardExpiryDate: String, cardCvv: String, cardHolder: String) {
        val date = cardExpiryDate.split("/").map { it.toInt() }
        val card = Card(number = cardNumber, exp_month = date[0], exp_year = date[1], cvc = cardCvv)

        disposables += repository.addCard(card, prefs.userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { cardStatus.value = NetworkViewState.Loading() }
                .subscribeBy(
                        onSuccess = {
                            logd("[addCard.onSuccess]")
                            cardStatus.value = NetworkViewState.Success(true)
                        },
                        onError = { e ->
                            loge("[addCard.onError]" + e.printStackTrace())
                            when (e) {
                                is ServerException -> e.message.notNull {
                                    cardStatus.value = NetworkViewState.Error(it)
                                }
                                is IOException -> cardStatus.value = NetworkViewState.Error(
                                        R.string.internet_connection_error)
                                else -> cardStatus.value = NetworkViewState.Error(
                                        R.string.generic_error)
                            }
                        }
                )
    }
}