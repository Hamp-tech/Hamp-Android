package com.hamp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.hamp.data.db.domain.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id = 1")
    fun getUser(): Single<User>

    @Insert(onConflict = REPLACE)
    fun saveUser(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}