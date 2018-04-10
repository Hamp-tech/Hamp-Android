package com.hamp.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.hamp.db.domain.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id = 1")
    fun getUser(): Single<User>

    @Query("SELECT * FROM User")
    fun getUsersAux(): List<User>

    @Insert(onConflict = REPLACE)
    fun saveUser(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}