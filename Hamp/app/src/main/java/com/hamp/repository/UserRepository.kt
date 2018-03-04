package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.domain.User
import com.hamp.domain.request.SignInRequest
import com.hamp.domain.response.UserResponse
import com.hamp.preferences.PreferencesManager
import com.vicpin.krealmextensions.save
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        val api: RestApi,
        val prefs: PreferencesManager
) {

    fun isFirstTime() = prefs.isFirstTime

    fun isUserLogin() = prefs.userId.isNotBlank()

    fun signUp(user: User) = api.signUp(user)

    fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    fun saveUser(user: User) {
        user.save()
        prefs.userId = user.identifier
    }

//    if (task.isSuccessful) {
//        val firebaseUser = task.result.user
//        prefs.userId = firebaseUser.uid
//        return api.createUserWithID(prefs.userId, name, surname, email, phone, birthday, gender, "")
//    } else {
//        return Completable.error(task.exception)
//    }

//    private var database = FirebaseDatabase.getInstance().getReference("users")
//    private var listener: ValueEventListener? = null
//
//    init {
//        database.child("users").child("882EbJY7fOd6KDySu8MFtXq2mvh1")
//    }
//
//    fun addListener(valueEventListener: ValueEventListener) {
//        listener = valueEventListener
//        database.addValueEventListener(listener)
//    }
//
//    fun removeListener() {
//        database.removeEventListener(listener)
//    }

}