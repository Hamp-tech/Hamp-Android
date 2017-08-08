package com.hamp.mvp.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Patterns
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.changeBackgroundTextWatcher
import com.hamp.extension.showErrorSnackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class SignUpActivity : BaseActivity(), View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    lateinit var datePicker: DatePickerDialog

    var errors = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initializeEditText()
        initializeDatePicker()

        signUpBday.onClick { openDatePicker() }
        enter.onClick { validateForm() }
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
        val finalMonth = month + 1
        signUpBday.setText("$dayOfMonth-$finalMonth-$year")
    }

    private fun openDatePicker() = datePicker.show()

    private fun validateForm() {
        if (checkName() && checkEmail() && checkPhone() && checkBDay()
                && checkPassword()) {

        } else {
            showErrors()
        }
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

    private fun checkPhone(): Boolean {
        return if (signUpPhone.text.matches(Regex("^[6-9][0-9]{8}$"))) {
            true
        } else {
            errors.add("Telefono invalido")
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
            errors.add("Contraseña invalida")
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

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v is EditText) {
            if (hasFocus) v.hint = ""
            else v.hint = v.tag as String
        }
    }
}
