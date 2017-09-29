package com.hamp.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthManager {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isUserLogin() = auth.currentUser == null

    fun signUpWithEmailAndPassword(email: String, password: String,
                                   onCompleteListener: OnCompleteListener<AuthResult>) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
    }

    fun loginWithEmailAndPassword(email: String, password: String,
                                  onCompleteListener: OnCompleteListener<AuthResult>) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
    }

    fun logout() = auth.signOut()
}