package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.db.dao.UserDao
import com.hamp.db.domain.User
import com.hamp.domain.request.SignInRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        val api: RestApi,
        private val userDao: UserDao
) {

    fun signUp(user: User) = api.signUp(user)

    fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    fun updateUser(user: User, userId: String) = api.updateUser(user, userId)

    fun getUser() = userDao.getUser()

    fun saveUser(user: User) = userDao.saveUser(user)

    fun deleteUser() = userDao.deleteAll()

}