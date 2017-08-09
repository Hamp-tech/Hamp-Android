package com.hamp.mvp.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Patterns
import android.view.View
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.changeBackgroundTextWatcher
import com.hamp.extension.hideKeyboard
import com.hamp.extension.showErrorSnackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_footer.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class LoginActivity : BaseActivity(), View.OnFocusChangeListener {

    var errors = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        enter.visibility = View.GONE

        initializeEditText()

        cancel.onClick { onBackPressed() }
        loginRememberPassword.onClick { }
        loginEnter.onClick { validateForm() }
    }

    private fun validateForm() {
        if (checkEmail() && checkPassword()) {
            doLogin()
        } else {
            hideKeyboard()
            showErrors()
        }
    }

    private fun checkEmail(): Boolean {
        val email = loginEmail.text.toString().trim()

        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) true
        else {
            errors.add("Email invalido")
            false
        }
    }

    private fun checkPassword(): Boolean {
        val password = loginPassword.text.toString().trim()

        return if (password.isNotBlank()) true
        else {
            errors.add("Contraseña invalida")
            false
        }
    }

    private fun doLogin() {
        showErrorSnackbar("LOGIN", Snackbar.LENGTH_LONG)
    }

    private fun showErrors() = showErrorSnackbar(errors.last(), Snackbar.LENGTH_LONG)

    private fun initializeEditText() {
        loginEmail.onFocusChangeListener = this
        loginPassword.onFocusChangeListener = this

        loginEmail.changeBackgroundTextWatcher()
        loginPassword.changeBackgroundTextWatcher()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v is EditText) {
            if (hasFocus) v.hint = ""
            else v.hint = v.tag as String
        }
    }
}
