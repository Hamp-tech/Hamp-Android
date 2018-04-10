package com.hamp.mvvm.home.profile

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.db.domain.User
import com.hamp.di.Injectable
import com.hamp.extensions.*
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.start.StartActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import javax.inject.Inject

class ProfileFragment : BaseFragment(), Injectable,
        DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var datePicker: DatePickerDialog
    private lateinit var validator: Validator

    @NotEmpty(messageResId = R.string.error_name_empty)
    private lateinit var name: EditText

    @Email(messageResId = R.string.error_email_empty)
    private lateinit var email: EditText

    @Length(min = 9, messageResId = R.string.error_phone_empty)
    private lateinit var phone: EditText

    private var editMode = false

    companion object {
        fun create() = ProfileFragment()
    }

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        initializeValidatorAndInputs()
        initializeDatePicker()
        setPreferences()
        setTerms()
        editModeSwitch()

        profileBirth.onClick { datePicker.show() }
        logout.onClick { doLogout() }
    }

    private fun setUpViewModel() {
        profileViewModel = getViewModel(this, viewModelFactory)

        profileViewModel.user.observe(this, { it?.let { fillProfileInfo(it) } })
        profileViewModel.loading.observe(this, { it?.let { showLoading(it) } })
        profileViewModel.updateSucceed.observe(this, { it?.let { if (it) updateSucceed() } })
        profileViewModel.updateError.observe(this, { it?.let { updateError(it) } })
    }

    private fun initializeValidatorAndInputs() {
        validator = Validator(this)
        validator.setValidationListener(this)

        name = profileName
        email = profileEmail
        phone = profilePhone
    }

    private fun initializeDatePicker() {
        val c: Calendar = Calendar.getInstance()

        val startYear: Int = c.get(Calendar.YEAR)
        val startMonth: Int = c.get(Calendar.MONTH)
        val startDay: Int = c.get(Calendar.DAY_OF_MONTH)

        datePicker = DatePickerDialog(context, this, startYear, startMonth, startDay)

        //Add 1h to day for lollipop
        datePicker.datePicker.maxDate = c.timeInMillis + (1000 * 60 * 60 * 1)

        c.add(Calendar.YEAR, -100)
        datePicker.datePicker.minDate = c.timeInMillis

        datePicker.setTitle(null)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        var monthString = (month + 1).toString()
        if (monthString.length == 1) monthString = "0$monthString"

        var dayString = dayOfMonth.toString()
        if (dayString.length == 1) dayString = "0$dayString"

        profileBirth.text = String.format(getString(R.string.date_format), dayString, monthString, year)
    }

    private fun fillProfileInfo(user: User) {
        profileName.setText(user.name)
        profileSurname.setText(user.surname)
        profileEmail.setText(user.email)
        profilePhone.setText(user.phone)
        profileBirth.text = user.birthday

        if (user.gender == "M") {
            maleRadioButton.isChecked = true
            femaleRadioButton.isChecked = false
        } else if (user.gender == "F") {
            maleRadioButton.isChecked = true
            femaleRadioButton.isChecked = false
        } else {
            maleRadioButton.isChecked = false
            femaleRadioButton.isChecked = false
        }
    }

    private fun setTerms() {
        val content = SpannableString(getString(R.string.terms_of_use))
        content.setSpan(UnderlineSpan(), 4, 15, 0)
        terms.text = content
    }

    fun editionMode() {
        if (!editMode) {
            editMode = true
            editModeSwitch()
        } else {
            validator.validate()
        }
    }

    private fun editModeSwitch() {
        profileName.setEditMode(editMode)
        profileSurname.setEditMode(editMode)
        profileEmail.setEditMode(editMode)
        profilePhone.setEditMode(editMode)
        phoneSwitch.isClickable = editMode
        profileBirth.isEnabled = editMode
        maleRadioButton.isClickable = editMode
        femaleRadioButton.isClickable = editMode
        segmentedButtonMorning.isEnabled = editMode
        segmentedButtonAfternoon.isEnabled = editMode
        rateHampSwitch.isClickable = editMode
        notificationsSwitch.isClickable = editMode

        homeActivity.editModeSwitch(editMode)
    }

    private fun doLogout() {
        profileViewModel.logout()
        startActivity(Intent(context, StartActivity::class.java))
        homeActivity.finish()
    }

    override fun onValidationSucceeded() {
        profileViewModel.user.value?.name = profileName.trim()
        profileViewModel.user.value?.surname = profileSurname.trim()
        profileViewModel.user.value?.email = profileEmail.trim()
        profileViewModel.user.value?.phone = profilePhone.trim()
        profileViewModel.user.value?.birthday = profileBirth.text.toString()

        if (maleRadioButton.isChecked) profileViewModel.user.value?.gender = "M"
        else if (femaleRadioButton.isChecked) profileViewModel.user.value?.gender = "F"
        else profileViewModel.user.value?.gender = "U"

        val pickUpTime = if (segmentedButtonMorning.isChecked) "M"
        else "A"

        profileViewModel.savePreferences(phoneSwitch.isChecked, rateHampSwitch.isChecked,
                notificationsSwitch.isChecked, pickUpTime)

        profileViewModel.user.value.notNull { profileViewModel.updateUser(it) }
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        homeActivity.hideKeyboard()

        errors?.forEach {
            val view = it.view
            val message = it.getCollatedErrorMessage(context)

            if (view is EditText) {
                view.error = message
                view.shake()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (editMode) {
            editMode = false
            profileViewModel.user.value.notNull { fillProfileInfo(it) }
            editModeSwitch()
            setPreferences()
        }
    }

    private fun setPreferences() {
        phoneSwitch.isChecked = profileViewModel.isPhoneAllowed
        rateHampSwitch.isChecked = profileViewModel.isRateAllowed
        notificationsSwitch.isChecked = profileViewModel.areNotificationsAllowed
        if (profileViewModel.pickUpTurn == "M") {
            segmentedButtonMorning.isChecked = true
            segmentedButtonAfternoon.isChecked = false
        } else {
            segmentedButtonMorning.isChecked = false
            segmentedButtonAfternoon.isChecked = true
        }
    }

    private fun updateSucceed() {
        editMode = false
        editModeSwitch()
    }

    private fun updateError(error: Any) = homeActivity.showErrorSnackBar(error, Snackbar.LENGTH_LONG)

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }
}