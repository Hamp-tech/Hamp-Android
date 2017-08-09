package com.hamp.mvp.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.changeBackgroundTextWatcher
import com.hamp.extension.hideKeyboard
import com.hamp.extension.setColorFilter
import com.hamp.extension.showErrorSnackbar
import com.hamp.mvp.home.HomeActivity
import com.hamp.mvp.view.HampLoadingView
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

@BaseActivity.Animation(BaseActivity.PUSH)
class SignUpActivity : BaseActivity(), SignUpContract.View,
        View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    lateinit private var presenter: SignUpPresenter
    lateinit private var datePicker: DatePickerDialog
//    lateinit private var loadingView: HampLoadingView

    var errors = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        loadingView = HampLoadingView(this)

        presenter = SignUpPresenter()
        presenter.setView(this)

        initializeEditText()
        initializeDatePicker()

        signUpBday.onClick { openDatePicker() }
        enter.onClick { validateForm() }
        cancel.onClick { onBackPressed() }
    }

    private fun initializeDatePicker() {
        val c: Calendar = Calendar.getInstance()

        val startYear: Int = c.get(Calendar.YEAR)
        val startMonth: Int = c.get(Calendar.MONTH)
        val startDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(this, this, startYear, startMonth, startDay)

        //Add 1h to day for lollipop
        datePicker.datePicker.maxDate = c.timeInMillis + (1000 * 60 * 60 * 1)

        c.add(Calendar.YEAR, -100)
        datePicker.datePicker.minDate = c.timeInMillis

        datePicker.setTitle(null)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        var monthString = (month + 1).toString()
        if (monthString.length == 1) monthString = "0" + monthString

        var dayString = dayOfMonth.toString()
        if (dayString.length == 1) dayString = "0" + dayString

        signUpBday.setText("$dayString/$monthString/$year")
        signUpBday.setColorFilter(ContextCompat.getColor(this, R.color.cerise_pink))
    }

    private fun openDatePicker() = datePicker.show()

    private fun validateForm() {
        if (checkName() && checkEmail() && checkPhone() && checkBDay()
                && checkPassword()) {
            doSignUp()
        } else {
            showErrors()
        }
    }

    private fun doSignUp() {
        hideKeyboard()

        val email = signUpEmail.text.toString().trim()
        val password = signUpPassword.text.toString().trim()

        presenter.signUp(email, password)
    }

    private fun checkName(): Boolean {
        val name = signUpName.text.toString().trim()

        return if (name.isNotBlank()) {
            true
        } else {
            errors.add("Nombre invalido")
            false
        }
    }

    private fun checkEmail(): Boolean {
        val email = signUpEmail.text.toString().trim()

        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) true
        else {
            errors.add("Email invalido")
            false
        }
    }

    private fun checkPhone(): Boolean {
        val phone = signUpPhone.text.toString().trim()

        return if (Patterns.PHONE.matcher(phone).matches() && phone.length >= 9) {
            true
        } else {
            errors.add("Telefono invalido")
            false
        }
    }

    private fun checkBDay(): Boolean {
        val bday = signUpBday.text.toString().trim()

        return if (bday.isNotBlank()) {
            true
        } else {
            errors.add("Fecha de nacimiento invalida")
            false
        }
    }

    private fun checkPassword(): Boolean {
        val password = signUpPassword.text.toString().trim()
        val confirmPassword = signUpConfirmPassword.text.toString().trim()

        return if (password.isNotBlank() && confirmPassword.isNotBlank()) {
            if (password == confirmPassword) {
                true
            } else {
                errors.add("Las contraseñas no coinciden")
                false
            }
        } else {
            errors.add("La contraseña debe tener al menos 8 caracteres")
            false
        }
    }

    private fun showErrors() {
        showErrorSnackbar(errors.last(), Snackbar.LENGTH_LONG)
    }

    private fun initializeEditText() {
        signUpName.onFocusChangeListener = this
        signUpEmail.onFocusChangeListener = this
        signUpPhone.onFocusChangeListener = this
        signUpPassword.onFocusChangeListener = this
        signUpConfirmPassword.onFocusChangeListener = this

        signUpName.changeBackgroundTextWatcher()
        signUpEmail.changeBackgroundTextWatcher()
        signUpPhone.changeBackgroundTextWatcher()
        signUpPassword.changeBackgroundTextWatcher()
        signUpConfirmPassword.changeBackgroundTextWatcher()
    }

    override fun showInternetNotAvailable() {
        showErrorSnackbar(getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG)
    }

    override fun isActive() = isRunning

    override fun showProgress(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
//        if (show) loadingView.show()
//        else loadingView.dialog.dismiss()
    }

    override fun signUpSucceed() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    override fun showSignUpError(message: String) {
        showErrorSnackbar(message, Snackbar.LENGTH_LONG)
    }

    override fun showError(message: Int) {
        showErrorSnackbar(getString(message), Snackbar.LENGTH_LONG)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v is EditText) {
            if (hasFocus) v.hint = ""
            else v.hint = v.tag as String
        }
    }
}
