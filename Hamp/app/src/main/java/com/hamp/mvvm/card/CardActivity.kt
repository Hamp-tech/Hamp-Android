package com.hamp.mvvm.card

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.common.NetworkViewState
import com.hamp.di.Injectable
import com.hamp.extensions.*
import com.hamp.mvvm.home.HomeActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.vicmikhailau.maskededittext.MaskedEditText
import kotlinx.android.synthetic.main.activity_payment_info.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.NONE)
class CardActivity : BaseActivity(), Injectable,
        Validator.ValidationListener, TextWatcher {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var cardViewModel: CardViewModel
    private lateinit var validator: Validator

    private var isReadyToValidate = false
    private var isFromBasket = false

//    @CreditCard(cardTypes = arrayOf(CreditCard.Type.VISA, CreditCard.Type.MASTERCARD))
//    lateinit var cardNumberValidate: MaskedEditText

    @Length(min = 19)
    private lateinit var cardNumberValidate: MaskedEditText

    @Length(min = 5)
    private lateinit var cardExpiryDateValidate: MaskedEditText

    @Length(min = 3)
    private lateinit var cardCvvValidate: EditText

    @NotEmpty
    private lateinit var cardHolderValidate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info)

        intent.extras?.let { isFromBasket = it.getBoolean("isFromBasket", false) }

        setUpViewModel()
        initializeValidatorAndInputs()

        skipContinueButton.onClick { skipContinue() }
    }

    private fun setUpViewModel() {
        cardViewModel = getViewModel(viewModelFactory)

        observe(cardViewModel.cardStatus, { it.notNull { cardStateBehaviour(it) } })
    }

    private fun cardStateBehaviour(networkViewState: NetworkViewState) {
        when (networkViewState) {
            is NetworkViewState.Loading -> showLoading(true)
            is NetworkViewState.Success<*> -> addCardSucceed()
            is NetworkViewState.Error -> {
                showLoading(false)
                addCardError(networkViewState.error)
            }
        }
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        cardNumberValidate = cardNumber
        cardExpiryDateValidate = cardExpiryDate
        cardCvvValidate = cardCvv
        cardHolderValidate = cardHolder

        cardNumber.addTextChangedListener(this)
        cardExpiryDate.addTextChangedListener(this)
        cardCvv.addTextChangedListener(this)
        cardHolder.addTextChangedListener(this)
    }

    private fun skipContinue() {
        if (isReadyToValidate) validator.validate()
        else {
            if (isFromBasket) onBackPressed()
            else goToHome()
        }
    }

    override fun onValidationSucceeded() = cardViewModel.addCard(cardNumber.trim(),
            cardExpiryDate.trim(),
            cardCvv.trim(),
            cardHolder.trim()
    )

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
//        hideKeyboard()
//        errors?.forEach {}

        showErrorSnackBar(duration = Snackbar.LENGTH_LONG)
    }

    private fun addCardSucceed() = goToHome()

    private fun addCardError(error: Any) = showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun goToHome() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (cardNumber.text.isNotBlank() && cardExpiryDate.text.isNotBlank() &&
                cardCvv.text.isNotBlank() && cardHolder.text.isNotBlank()) {
            isReadyToValidate = true
            skipContinueButton.text = getString(R.string.card_continue)
        } else {
            isReadyToValidate = false
            skipContinueButton.text = getString(R.string.card_skip)
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}
