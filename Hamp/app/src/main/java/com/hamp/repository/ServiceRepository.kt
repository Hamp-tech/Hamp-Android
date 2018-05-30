package com.hamp.repository

import com.hamp.data.db.domain.ServiceQuantity
import com.hamp.domain.Service
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Repository to handle user
 */
interface ServiceRepository {

    /**
     * Get Services from the API
     */
    fun loadServices(): Single<List<Service>>

    /**
     * Get Services from the DB
     */
    fun getServices(): Single<List<Service>>

    /**
     * Get ServicesQuantity from the DB
     */
    fun getServicesQuantity(): Single<List<ServiceQuantity>>

    /**
     * Insert Service into the database
     *
     * @param service Service to insert
     */
    fun saveServiceQuantity(service: Service): Completable

    /**
     * Delete all rows from the ServiceQuantity table
     */
    fun deleteBasket(): Completable
}