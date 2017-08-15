package com.hamp.mvp.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.hideKeyboard
import com.hamp.extension.showErrorSnackbar
import com.hamp.mvp.home.HomeActivity
import com.hamp.mvp.view.HampEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Password
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_footer.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class LoginActivity : BaseActivity(), LoginContract.View, Validator.ValidationListener {

    lateinit private var presenter: LoginPresenter
    lateinit private var validator: Validator

    @Email(messageResId = R.string.error_email_empty)
    lateinit var email: HampEditText

    @Password(min = 6, messageResId = R.string.error_password_length)
    lateinit var password: HampEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        presenter = LoginPresenter()
        presenter.setView(this)

        initializeValidatorAndInputs()

        enter.visibility = View.GONE

        cancel.onClick { onBackPressed() }
        loginRememberPassword.onClick { }
        loginEnter.onClick { validator.validate() }
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        email = loginEmail
        password = loginPassword
    }

    override fun onValidationSucceeded() = doLogin()

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        hideKeyboard()

        errors?.forEach {
            val view = it.view
            val message = it.getCollatedErrorMessage(this)

            if (view is HampEditText) view.error = message
        }
    }

    private fun doLogin() {
        hideKeyboard()

        val email = loginEmail.text.toString().trim()
        val password = loginPassword.text.toString().trim()

        presenter.login(email, password)
    }

    override fun showProgress(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

    override fun loginSucceed() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    override fun loginError(message: String) = showErrorSnackbar(message, Snackbar.LENGTH_LONG)
}