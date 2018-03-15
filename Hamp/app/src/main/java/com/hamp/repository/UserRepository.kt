package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.domain.User
import com.hamp.domain.request.SignInRequest
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        val api: RestApi
) {

    fun signUp(user: User) = api.signUp(user)

    fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    fun updateUser(user: User, userId: String) = api.updateUser(user, userId)

    fun getUser() = User().queryFirst()

    fun saveUser(user: User) = user.save()

    fun deleteUser() = User().deleteAll()

}