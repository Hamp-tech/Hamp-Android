package com.hamp.ui.paymentInfo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.hideKeyboard
import com.hamp.ui.home.HomeActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.vicmikhailau.maskededittext.MaskedEditText
import kotlinx.android.synthetic.main.activity_payment_info.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.NONE)
class PaymentInfoActivity : BaseActivity(), Validator.ValidationListener, TextWatcher {

    lateinit private var validator: Validator

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

    @NotEmpty(messageResId = R.string.error_password_length)
    private lateinit var cardHolderValidate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info)

        intent.extras?.let { isFromBasket = it.getBoolean("isFromBasket", false) }

        initializeValidatorAndInputs()

        skipContinueButton.onClick { skipContinue() }
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
            if (isFromBasket) {
                onBackPressed()
            } else {
                startActivity(intentFor<HomeActivity>()
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

                finish()
            }
        }
    }

    override fun onValidationSucceeded() {
        Log.d("HOLAA", "FUNCIONA")
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        hideKeyboard()

        errors?.forEach {

        }
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
