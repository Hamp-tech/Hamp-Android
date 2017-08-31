package com.hamp.ui.paymentInfo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.hideKeyboard
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.vicmikhailau.maskededittext.MaskedEditText
import kotlinx.android.synthetic.main.activity_payment_info.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.NONE)
class PaymentInfoActivity : BaseActivity(), Validator.ValidationListener {
    lateinit private var validator: Validator

//    @CreditCard(cardTypes = arrayOf(CreditCard.Type.VISA, CreditCard.Type.MASTERCARD))
//    lateinit var cardNumberValidate: MaskedEditText

    @Length(min = 19)
    lateinit var cardNumberValidate: MaskedEditText

    @Length(min = 5)
    lateinit var cardExpiryDateValidate: MaskedEditText

    @Length(min = 3)
    lateinit var cardCvvValidate: EditText

    @NotEmpty(messageResId = R.string.error_password_length)
    lateinit var cardholderValidate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info)

        initializeValidatorAndInputs()

        skipContinueButton.onClick { validator.validate() }
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        cardNumberValidate = cardNumber
        cardExpiryDateValidate = cardExpiryDate
        cardCvvValidate = cardCvv
        cardholderValidate = cardholder
    }

    override fun onValidationSucceeded() {
        Log.d("HOLAA", "FUNCIONA")
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        hideKeyboard()

        errors?.forEach {

        }
    }
}
