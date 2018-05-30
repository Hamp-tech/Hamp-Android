package com.hamp.mvvm.login

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.common.NetworkViewState
import com.hamp.di.Injectable
import com.hamp.extensions.*
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.restore.RestoreActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_footer.*
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

        //Bug keyboard when window with background drawable
        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        setUpViewModel()

        loginEnter.onClick { validateForm() }
        loginRememberPassword.onClick { goToRememberPassword() }
        cancel.onClick { onBackPressed() }
    }

    private fun validateForm() = loginViewModel.validateForm(loginEmail.trim(), loginPassword.trim())

    private fun setUpViewModel() {
        loginViewModel = getViewModel(viewModelFactory)

        observe(loginViewModel.validationStatus, { it?.let { validatorStateBehaviour(it) } })
        observe(loginViewModel.loginStatus, { it?.let { loginStateBehaviour(it) } })
    }

    private fun validatorStateBehaviour(networkViewState: NetworkViewState) {
        when (networkViewState) {
            is NetworkViewState.Loading -> showLoading(networkViewState.show)
            is NetworkViewState.Success<*> -> validationSucceeded()
            is NetworkViewState.Error -> validationFailed(networkViewState.error)
        }
    }

    private fun loginStateBehaviour(networkViewState: NetworkViewState) {
        when (networkViewState) {
            is NetworkViewState.Loading -> showLoading(networkViewState.show)
            is NetworkViewState.Success<*> -> loginSucceed()
            is NetworkViewState.Error -> loginError(networkViewState.error)
        }
    }

    private fun validationSucceeded() = doLogin()

    private fun validationFailed(errors: Any) {
        if (errors is List<*> && errors.isNotEmpty()) {
            hideKeyboard()

            errors.forEach {
                when (it as? Int) {
                    R.string.error_email_empty -> loginEmail.error = getString(it)
                    R.string.error_password_length -> loginPassword.error = getString(it)
                }
            }
        }
    }

    private fun doLogin() {
        hideKeyboard()
        loginViewModel.login(loginEmail.trim(), loginPassword.trim())
    }

    private fun loginSucceed() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    private fun loginError(error: Any) = showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun goToRememberPassword() = startActivity<RestoreActivity>()

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

}