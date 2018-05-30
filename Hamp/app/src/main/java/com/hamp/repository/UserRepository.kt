package com.hamp.repository

import com.hamp.data.db.domain.User
import com.hamp.domain.request.SignInRequest
import com.hamp.domain.response.UserResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Repository to handle user
 */
interface UserRepository {

    /**
     * Create user (SignUp)
     *
     * @param user User with the information required
     */
    fun signUp(user: User): Single<UserResponse>

    /**
     * Login
     *
     * @param signInRequest Email and password
     */
    fun signIn(signInRequest: SignInRequest): Single<UserResponse>

    /**
     * Update user from the API
     *
     * @param user User with the information updated
     * @param userId user Id
     */
    fun updateUser(user: User, userId: String): Single<UserResponse>

    /**
     * Get user from the database
     */
    fun getDBUser(): Single<User>

    /**
     * Insert user into the database
     *
     * @param user The logged user
     */
    fun saveUser(user: User): Completable

    /**
     * Delete user from the database
     */
    fun deleteUser(): Completable
}