package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.db.dao.UserDao
import com.hamp.db.domain.User
import com.hamp.domain.request.SignInRequest
import io.reactivex.Completable
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

    fun getDBUser() = userDao.getUser()

    fun saveUser(user: User): Completable {
        return Completable.fromCallable { userDao.saveUser(user) }
    }

    fun deleteUser(): Completable {
        return Completable.fromCallable { userDao.deleteAll() }
    }
}