package com.hamp.domain

import android.os.Parcel
import android.os.Parcelable

data class ServiceQuantity(
        val service: Service,
        var quantity: Int
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ServiceQuantity> = object : Parcelable.Creator<ServiceQuantity> {
            override fun createFromParcel(source: Parcel): ServiceQuantity = ServiceQuantity(source)
            override fun newArray(size: Int): Array<ServiceQuantity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<Service>(Service::class.java.classLoader),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(service, 0)
        writeInt(quantity)
    }
}