package com.hamp.db.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
        foreignKeys = [(ForeignKey(entity = HampService::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("serviceId"),
                onDelete = ForeignKey.CASCADE))]
)
data class ServiceQuantity(
        @PrimaryKey
        val serviceId: String,
        var amount: Int
)