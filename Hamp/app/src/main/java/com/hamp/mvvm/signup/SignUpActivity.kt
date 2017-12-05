package com.hamp.mvvm.signup

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.DatePicker
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.mvvm.extensions.hideKeyboard
import com.hamp.mvvm.extensions.observe
import com.hamp.mvvm.extensions.trim
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.signup_login_toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class SignUpActivity : BaseActivity(), DatePickerDialog.OnDateSetListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit private var signUpViewModel: SignUpViewModel
    lateinit private var datePicker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        restorePassword.visibility = View.GONE

        setUpViewModel()
        setUpDatePicker()

        signUpBday.onClick { datePicker.show() }
        enter.onClick {
            signUpViewModel.validateForm(signUpName.trim(), signUpSurname.trim(),
                    signUpEmail.trim(), signUpPhone.trim(), signUpBday.trim(),
                    signUpPassword.trim(), signUpConfirmPassword.trim())
        }
        cancel.onClick { onBackPressed() }
    }

    private fun setUpViewModel() {
        signUpViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SignUpViewModel::class.java)

        signUpViewModel.loading.observe(this, { showLoading(it ?: false) })
        signUpViewModel.validationSucceeded.observe(this, { validationSucceeded(it ?: false) })
        signUpViewModel.validationErrors.observe(this, { validationFailed(it ?: emptyList()) })
    }

    private fun setUpDatePicker() {
        val c: Calendar = Calendar.getInstance()

        val startYear: Int = c.get(Calendar.YEAR)
        val startMonth: Int = c.get(Calendar.MONTH)
        val startDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(this, this, startYear, startMonth, startDay)

        // Add 1h to day for lollipop
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

    private fun validationSucceeded(succeeded: Boolean) {
        if (succeeded) doSignUp()
    }

    private fun validationFailed(errors: List<Int>) {
        if (errors.isNotEmpty()) {
            hideKeyboard()
            errors.forEach {
                when (it) {
                    R.string.error_name_empty -> signUpName.error = getString(it)
                    R.string.error_surname_empty -> signUpSurname.error = getString(it)
                    R.string.error_email_empty -> signUpEmail.error = getString(it)
                    R.string.error_phone_empty -> signUpPhone.error = getString(it)
                    R.string.error_birthday_empty -> signUpBday.error = getString(it)
                    R.string.error_password_length -> signUpPassword.error = getString(it)
                    R.string.error_confirm_password -> signUpConfirmPassword.error = getString(it)
                }
            }
        }
    }

    private fun doSignUp() {
        hideKeyboard()

        val gender = when {
            maleRadioButton.isChecked -> "M"
            femaleRadioButton.isChecked -> "F"
            else -> "U"
        }

        signUpViewModel.signUp(signUpEmail.trim(), signUpPassword.text.toString().trim(),
                signUpName.text.toString().trim(), signUpSurname.text.toString().trim(),
                signUpPhone.text.toString().trim(), signUpBday.text.toString(), gender)
    }

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }

//
//    override fun signUpSucceed() {
//        startActivity(intentFor<PaymentInfoActivity>()
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
//
//        finish()
//    }
//
//    override fun signUpError(message: String) = showErrorSnackBar(message, Snackbar.LENGTH_LONG)
}