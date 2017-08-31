package com.hamp.ui.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.DatePicker
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.extension.hideKeyboard
import com.hamp.extension.showErrorSnackbar
import com.hamp.ui.home.HomeActivity
import com.hamp.ui.views.HampEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

@BaseActivity.Animation(BaseActivity.PUSH)
class SignUpActivity : BaseActivity(), SignUpContract.View,
        DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    lateinit private var presenter: SignUpPresenter
    lateinit private var datePicker: DatePickerDialog
    lateinit private var validator: Validator

    @NotEmpty(messageResId = R.string.error_name_empty)
    lateinit var name: HampEditText

    @Email(messageResId = R.string.error_email_empty)
    lateinit var email: HampEditText

    @Length(min = 9, messageResId = R.string.error_phone_empty)
    lateinit var phone: HampEditText

    @NotEmpty(messageResId = R.string.error_birthday_empty)
    lateinit var birthday: HampEditText

    @Password(min = 6, messageResId = R.string.error_password_length)
    lateinit var password: HampEditText

    @ConfirmPassword(messageResId = R.string.error_confirm_password)
    lateinit var confirmPassword: HampEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        presenter = SignUpPresenter()
        presenter.setView(this)

        initializeValidatorAndInputs()
        initializeDatePicker()

        signUpBday.onClick { openDatePicker() }
        enter.onClick { validator.validate() }
        cancel.onClick { onBackPressed() }
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        name = signUpName
        email = signUpEmail
        phone = signUpPhone
        birthday = signUpBday
        password = signUpPassword
        confirmPassword = signUpConfirmPassword
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

    override fun onValidationSucceeded() = doSignUp()

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        hideKeyboard()

        errors?.forEach {
            val view = it.view
            val message = it.getCollatedErrorMessage(this)

            if (view is HampEditText) view.error = message
        }
    }

    private fun doSignUp() {
        hideKeyboard()
        presenter.signUp(email.text.toString().trim(), password.text.toString().trim())
    }

    override fun showProgress(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

    override fun signUpSucceed() {
        startActivity(intentFor<HomeActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    override fun signUpError(message: String) = showErrorSnackbar(message, Snackbar.LENGTH_LONG)
}