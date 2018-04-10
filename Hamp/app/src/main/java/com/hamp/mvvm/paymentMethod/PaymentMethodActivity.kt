package com.hamp.mvvm.paymentMethod

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.domain.Card
import com.hamp.extensions.getViewModel
import com.hamp.extensions.notNull
import com.hamp.extensions.observe
import com.hamp.extensions.showErrorSnackBar
import com.hamp.mvvm.card.CardActivity
import kotlinx.android.synthetic.main.activity_payment_method.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class PaymentMethodActivity : BaseActivity(), Injectable, PaymentMethodAdapter.PaymentMethodListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var paymentMethodViewModel: PaymentMethodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        setUpViewModel()
        setUpRecyclerServices()

        back.onClick { onBackPressed() }
        finalizePurchaseButton.onClick { finalizePurchase() }
    }

    override fun onResume() {
        super.onResume()
        paymentMethodViewModel.getCards()
    }

    private fun setUpViewModel() {
        paymentMethodViewModel = getViewModel(viewModelFactory)
        paymentMethodViewModel.loading.observe(this, { it.notNull { showLoading(it) } })
        paymentMethodViewModel.cards.observe(this, { it.notNull { setCardsList(it) } })
        paymentMethodViewModel.transactionSucceeded.observe(this, { it.notNull { transactionSucceed(it) } })
        paymentMethodViewModel.transactionError.observe(this, { it.notNull { transactionError(it) } })

        paymentMethodViewModel.totalPrice = intent.extras.getDouble("price")
    }

    private fun setUpRecyclerServices() {
        paymentMethodList.setHasFixedSize(true)
        paymentMethodList.layoutManager = LinearLayoutManager(this)
    }

    private fun setCardsList(cards: List<Card>) {
        val recyclerCards: MutableList<Card?> = mutableListOf()
        recyclerCards.addAll(cards)
        recyclerCards.add(null)

        paymentMethodList.adapter = PaymentMethodAdapter(this, recyclerCards.toList(), this)
    }

    override fun creditCardListener(card: Card) {
        paymentMethodViewModel.currentCard = card
    }

    override fun addCardClick() {
        startActivity<CardActivity>("isFromBasket" to true)
    }

    private fun finalizePurchase() = paymentMethodViewModel.finalizePurchase()

    private fun transactionSucceed(bool: Boolean) {

    }

    private fun transactionError(error: Any) = showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }
}
