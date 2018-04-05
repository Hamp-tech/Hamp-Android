package com.hamp.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hamp.db.domain.HampService
import com.hamp.db.domain.ServiceQuantity
import com.hamp.domain.Service
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ServiceQuantityDao {

    @Query("SELECT * FROM ServiceQuantity, HampService WHERE ServiceQuantity.serviceId = HampService.id")
    fun getServicesQuantityDao(): Single<List<Service>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveServices(hampService: HampService): Completable

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveServicesQuantities(serviceQuantity: ServiceQuantity): Completable
}