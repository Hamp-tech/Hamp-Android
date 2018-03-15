package com.hamp.mvvm.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.extensions.hideKeyboard
import com.hamp.extensions.observe
import com.hamp.extensions.showErrorSnackBar
import com.hamp.extensions.trim
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.restore.RestoreActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_footer.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class LoginActivity : BaseActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        setUpViewModel()

        enter.visibility = View.GONE
        restorePassword.visibility = View.GONE

        loginEnter.onClick { validateForm() }
        loginRememberPassword.onClick { goToRememberPassword() }
        cancel.onClick { onBackPressed() }
    }

    private fun validateForm() = loginViewModel.validateForm(loginEmail.trim(), loginPassword.trim())

    private fun setUpViewModel() {
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        loginViewModel.loading.observe(this, { it?.let { showLoading(it) } })
        loginViewModel.validationSucceeded.observe(this, { it?.let { if (it) validationSucceeded() } })
        loginViewModel.validationErrors.observe(this, { it?.let { validationFailed(it) } })
        loginViewModel.loginError.observe(this, { it?.let { loginError(it) } })
        loginViewModel.loginSucceed.observe(this, { it?.let { if (it) loginSucceed() } })
    }

    private fun validationSucceeded() = doLogin()

    private fun validationFailed(errors: List<Int>) {
        if (errors.isNotEmpty()) hideKeyboard()
        errors.forEach {
            when (it) {
                R.string.error_email_empty -> loginEmail.error = getString(it)
                R.string.error_password_length -> loginPassword.error = getString(it)
            }
        }
    }

    private fun doLogin() {
        hideKeyboard()
        loginViewModel.login(loginEmail.trim(), loginPassword.trim())
    }

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

    private fun loginSucceed() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    private fun loginError(error: Any) = showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun goToRememberPassword() = startActivity<RestoreActivity>()
}