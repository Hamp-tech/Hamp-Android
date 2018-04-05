package com.hamp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.hamp.HampApplication
import com.hamp.db.dao.ServiceQuantityDao
import com.hamp.db.dao.UserDao
import com.hamp.db.domain.HampService
import com.hamp.db.domain.ServiceQuantity
import com.hamp.db.domain.User

@Database(entities = [(HampService::class), (User::class), (ServiceQuantity::class)],
        version = HampDatabase.VERSION)
abstract class HampDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1

        fun retrieveDB(app: HampApplication): HampDatabase {
            val hampDatabase = Room.databaseBuilder(app, HampDatabase::class.java, "hamp.db").build()

//            if (!prefs.isDBPopulated) {
//                Completable.fromCallable { populateDB(app, prosegurDatabase) }
//                        .subscribeOn(Schedulers.io())
//                        .subscribe({
//                            prefs.isDBPopulated = true
//                        }, {})
//            }

            return hampDatabase
        }
    }

    abstract fun userDao(): UserDao
    abstract fun serviceQuantityDao(): ServiceQuantityDao
}