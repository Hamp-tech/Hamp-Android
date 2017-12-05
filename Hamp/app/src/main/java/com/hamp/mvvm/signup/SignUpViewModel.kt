package com.hamp.mvvm.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Patterns
import com.hamp.R
import com.hamp.repository.UserRepository
import com.mobsandgeeks.saripaar.Validator
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
        private val repository: UserRepository
) : ViewModel() {

    lateinit private var validator: Validator

    val loading = MutableLiveData<Boolean>()
    val validationErrors = MutableLiveData<List<Int>>()
    val validationSucceeded = MutableLiveData<Boolean>()

    fun validateForm(signUpName: String, signUpSurname: String, signUpEmail: String,
                     signUpPhone: String, signUpBday: String, signUpPassword: String,
                     signUpConfirmPassword: String) {

        val errors = arrayListOf<Int>()

        if (signUpName.isEmpty()) errors.add(R.string.error_name_empty)
        if (signUpSurname.isEmpty()) errors.add(R.string.error_surname_empty)
        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()) errors.add(R.string.error_email_empty)
        if (signUpPhone.length < 9) errors.add(R.string.error_phone_empty)
        if (signUpBday.isEmpty()) errors.add(R.string.error_birthday_empty)
        if (signUpPassword.length < 6) errors.add(R.string.error_password_length)
        if (signUpConfirmPassword == signUpPassword) errors.add(R.string.error_confirm_password)

        if (errors.isEmpty()) {
            validationSucceeded.value = true
            validationErrors.value = emptyList()
        } else {
            validationErrors.value = errors
            validationSucceeded.value = false
        }
    }

    fun signUp(email: String, password: String, name: String, surname: String, phone: String,
               birthday: String, gender: String) {
        loading.value = true
        repository.signUp(email, password, name, surname, phone, birthday, gender)
    }
}