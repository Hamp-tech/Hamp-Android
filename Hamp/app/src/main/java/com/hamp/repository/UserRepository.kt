package com.hamp.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.hamp.api.RestApi
import com.hamp.firebase.FirebaseAuthManager
import com.hamp.mvvm.utils.PreferencesUtils
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        val api: RestApi,
        val auth: FirebaseAuthManager,
        val prefs: PreferencesUtils
) {

    fun signUp(email: String, password: String, name: String, surname: String, phone: String,
               birthday: String, gender: String): Completable {
        var completable: Completable? = null
        auth.signUpWithEmailAndPassword(email, password, OnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = task.result.user
                prefs.userId = firebaseUser.uid
                return api.createUserWithID(prefs.userId, name, surname, email, phone, birthday, gender, "")
            } else {
                return Completable.error(task.exception)
            }
        })
    }

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