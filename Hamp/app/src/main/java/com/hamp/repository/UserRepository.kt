package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.db.dao.UserDao
import com.hamp.db.domain.User
import com.hamp.domain.request.SignInRequest
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
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

    fun saveUser(user: User) {
        Completable.fromCallable { userDao.saveUser(user) }.subscribeOn(Schedulers.io())
    }

    fun deleteUser() {
        Completable.fromCallable { userDao.deleteAll() }.subscribeOn(Schedulers.io())
    }
}