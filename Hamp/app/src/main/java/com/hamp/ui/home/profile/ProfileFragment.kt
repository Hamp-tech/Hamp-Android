package com.hamp.ui.home.profile

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import com.hamp.R
import com.hamp.auth
import com.hamp.common.BaseFragment
import com.hamp.domain.User
import com.hamp.extension.hideKeyboard
import com.hamp.extension.setEditMode
import com.hamp.extension.shake
import com.hamp.prefs
import com.hamp.ui.home.HomeActivity
import com.hamp.ui.start.StartActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Length
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class ProfileFragment : BaseFragment(), DatePickerDialog.OnDateSetListener, Validator.ValidationListener {
    companion object {
        fun create() = ProfileFragment().apply { }
    }

    lateinit private var datePicker: DatePickerDialog
    lateinit private var validator: Validator

    @NotEmpty(messageResId = R.string.error_name_empty)
    lateinit private var name: EditText

    @Email(messageResId = R.string.error_email_empty)
    lateinit private var email: EditText

    @Length(min = 9, messageResId = R.string.error_phone_empty)
    lateinit private var phone: EditText

    private var provName = ""
    private var provSurname = ""
    private var provEmail = ""
    private var provPhone = ""
    private var provPhoneSwitch = false
    private var provBDay = ""
    private var provMaleRB = false
    private var provFemaleRB = false
    private var provRateHamp = false
    private var provNotifications = true

    private var editMode = false

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_profile, container, false)

    override fun onResume() {
        super.onResume()
        val profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileViewModel.getUser().observe(this, Observer<User> {
            Log.d("HOLAAAAAAA", it?.toString())
        })
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeValidatorAndInputs()
        initializeDatePicker()
        setTerms()

        editModeSwitch()

        profileBirth.onClick { datePicker.show() }

        logout.onClick { doLogout() }
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
        if (monthString.length == 1) monthString = "0" + monthString

        var dayString = dayOfMonth.toString()
        if (dayString.length == 1) dayString = "0" + dayString

        profileBirth.text = "$dayString/$monthString/$year"
    }

    private fun setTerms() {
        val content = SpannableString(getString(R.string.terms_of_use))
        content.setSpan(UnderlineSpan(), 4, 15, 0)
        terms.text = content
    }

    fun editionMode() {
        if (!editMode) {
            editMode = true
            provName = profileName.text.toString()
            provSurname = profileSurname.text.toString()
            provEmail = profileEmail.text.toString()
            provPhone = profilePhone.text.toString()
            provPhoneSwitch = phoneSwitch.isChecked
            provBDay = profileBirth.text.toString()
            provMaleRB = maleRadioButton.isChecked
            provFemaleRB = femaleRadioButton.isChecked
            provRateHamp = rateHampSwitch.isChecked
            provNotifications = notificationsSwitch.isChecked
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
        rateHampSwitch.isClickable = editMode
        notificationsSwitch.isClickable = editMode

        homeActivity.editModeSwitch(editMode)
    }

    private fun doLogout() {
        auth.logout()
        prefs.userId = ""
        startActivity(Intent(context, StartActivity::class.java))
        homeActivity.finish()
    }

    override fun onValidationSucceeded() {
        editMode = false
        editModeSwitch()
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden && editMode) {
            editMode = false
            profileName.setText(provName)
            profileSurname.setText(provSurname)
            profileEmail.setText(provEmail)
            profilePhone.setText(provPhone)
            phoneSwitch.isChecked = provPhoneSwitch
            profileBirth.text = provBDay
            maleRadioButton.isChecked = provMaleRB
            femaleRadioButton.isChecked = provFemaleRB
            rateHampSwitch.isChecked = provRateHamp
            notificationsSwitch.isChecked = provNotifications

            editModeSwitch()
        }
    }
}