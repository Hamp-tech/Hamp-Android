package com.hamp.mvvm.signup

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.common.NetworkViewState
import com.hamp.di.Injectable
import com.hamp.extensions.*
import com.hamp.mvvm.card.CardActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class SignUpActivity : BaseActivity(), Injectable, DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var datePicker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Bug keyboard when window with background drawable
        window.setBackgroundDrawableResource(R.drawable.start_bg_white)

        setUpViewModel()
        setUpDatePicker()
        setUpRadioButtons()

        signUpBday.onClick { datePicker.show() }
        enter.onClick { validateForm() }
        cancel.onClick { onBackPressed() }
    }

    private fun setUpViewModel() {
        signUpViewModel = getViewModel(viewModelFactory)

        observe(signUpViewModel.validationStatus, { it?.let { validatorStateBehaviour(it) } })
        observe(signUpViewModel.signUpStatus, { it?.let { signUpStateBehaviour(it) } })
    }

    private fun setUpDatePicker() {
        val c: Calendar = Calendar.getInstance()

        val startYear: Int = c.get(Calendar.YEAR)
        val startMonth: Int = c.get(Calendar.MONTH)
        val startDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(this, this, startYear, startMonth, startDay)

        c.add(Calendar.YEAR, -15)
        datePicker.datePicker.maxDate = c.timeInMillis + (1000 * 60 * 60 * 1) //Lollipop bug +1h

        c.add(Calendar.YEAR, -80)
        datePicker.datePicker.minDate = c.timeInMillis

        datePicker.setTitle(null)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var monthString = (month + 1).toString()
        if (monthString.length == 1) monthString = "0$monthString"

        var dayString = dayOfMonth.toString()
        if (dayString.length == 1) dayString = "0$dayString"

        signUpBday.setText(String.format(getString(R.string.date_format), dayString, monthString, year))
        signUpBday.setColorFilter(ContextCompat.getColor(this, R.color.cerise_pink))
    }

    private fun setUpRadioButtons() {
        ViewCompat.setLayoutDirection(maleRadioButton, ViewCompat.LAYOUT_DIRECTION_RTL)
        ViewCompat.setLayoutDirection(femaleRadioButton, ViewCompat.LAYOUT_DIRECTION_RTL)
    }

    private fun validateForm() {
        signUpViewModel.validateForm(signUpName.trim(), signUpSurname.trim(),
                signUpEmail.trim(), signUpPhone.trim(), signUpBday.trim(),
                signUpPassword.trim(), signUpConfirmPassword.trim())
    }

    private fun validatorStateBehaviour(networkViewState: NetworkViewState) {
        when (networkViewState) {
            is NetworkViewState.Success<*> -> validationSucceeded()
            is NetworkViewState.Error -> validationFailed(networkViewState.error)
        }
    }

    private fun signUpStateBehaviour(networkViewState: NetworkViewState) {
        when (networkViewState) {
            is NetworkViewState.Loading -> showLoading(true)
            is NetworkViewState.Success<*> -> signUpSucceed()
            is NetworkViewState.Error -> {
                showLoading(false)
                signUpError(networkViewState.error)
            }
        }
    }

    private fun validationSucceeded() = doSignUp()

    private fun validationFailed(errors: Any) {
        if (errors is List<*> && errors.isNotEmpty()) {
            hideKeyboard()
            errors.forEach {
                when (it as? Int) {
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

        val radioButtonId = genderRadioGroup.checkedRadioButtonId
        val gender = if (radioButtonId == -1) "U"
        else genderRadioGroup.findViewById<RadioButton>(radioButtonId).tag as String

        signUpViewModel.signUp(signUpEmail.trim(), signUpPassword.text.toString().trim(),
                signUpName.text.toString().trim(), signUpSurname.text.toString().trim(),
                signUpPhone.text.toString().trim(), signUpBday.text.toString(), gender)
    }

    private fun signUpError(error: Any) = showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun signUpSucceed() {
        startActivity(intentFor<CardActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))

        finish()
    }

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }
}