package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.db.dao.UserDao
import com.hamp.db.domain.User
import com.hamp.domain.request.SignInRequest
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val api: RestApi,
        private val userDao: UserDao
) : UserRepository {

    override fun signUp(user: User) = api.signUp(user)

    override fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    override fun updateUser(user: User, userId: String) = api.updateUser(user, userId)

    override fun getDBUser() = userDao.getUser()

    override fun saveUser(user: User): Completable {
        return Completable.fromCallable { userDao.saveUser(user) }
    }

    override fun deleteUser(): Completable {
        return Completable.fromCallable { userDao.deleteAll() }
    }
}