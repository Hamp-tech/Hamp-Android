package com.hamp.domain

import android.arch.persistence.room.Embedded
import android.os.Parcel
import android.os.Parcelable
import com.hamp.db.domain.HampService

data class Service(
        @Embedded var hampService: HampService,
        var quantity: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<HampService>(HampService::class.java.classLoader),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(hampService, 0)
        writeInt(quantity)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Service> = object : Parcelable.Creator<Service> {
            override fun createFromParcel(source: Parcel): Service = Service(source)
            override fun newArray(size: Int): Array<Service?> = arrayOfNulls(size)
        }
    }
}