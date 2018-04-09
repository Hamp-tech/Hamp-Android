package com.hamp.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hamp.db.domain.HampService
import com.hamp.db.domain.ServiceQuantity
import com.hamp.domain.Service
import io.reactivex.Single

@Dao
interface ServiceQuantityDao {

    @Query("SELECT * FROM HampService " +
            "WHERE HampService.id = :serviceId")
    fun getHampService(serviceId: String): HampService?

    @Insert
    fun saveHampService(hampService: HampService)

    @Update
    fun updateHampService(hampService: HampService)

    @Query("SELECT * FROM ServiceQuantity " +
            "WHERE ServiceQuantity.serviceId = :serviceId")
    fun getServiceQuantity(serviceId: String): ServiceQuantity?

    @Insert
    fun saveServiceQuantity(serviceQuantity: ServiceQuantity)

    @Update
    fun updateServiceQuantity(serviceQuantity: ServiceQuantity)

    @Query("SELECT * FROM ServiceQuantity, HampService WHERE ServiceQuantity.serviceId = HampService.id")
    fun getServicesQuantityDao(): Single<List<Service>>

    @Query("DELETE FROM ServiceQuantity")
    fun deleteAll()
}