package com.hamp.ui.restore

import android.os.Bundle
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.hideKeyboard
import com.hamp.ui.views.HampEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import kotlinx.android.synthetic.main.activity_restore.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class RestoreActivity : BaseActivity(), Validator.ValidationListener {
    lateinit private var validator: Validator

    @Email(messageResId = R.string.error_email_empty)
    lateinit var email: HampEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        toolbarImage.visibility = View.GONE
        enter.visibility = View.GONE

        initializeValidatorAndInputs()

        cancel.onClick { onBackPressed() }
        sendButton.onClick { validator.validate() }
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        email = recoverEmail
    }

    override fun onValidationSucceeded() {

    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        hideKeyboard()

        errors?.forEach {
            val view = it.view
            val message = it.getCollatedErrorMessage(this)

            if (view is HampEditText) view.error = message
        }
    }
}
