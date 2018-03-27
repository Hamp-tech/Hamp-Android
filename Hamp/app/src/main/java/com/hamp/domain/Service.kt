package com.hamp.domain

import android.os.Parcel
import android.os.Parcelable

data class Service(
        val id: String,
        val name: String,
        val image: Int,
        val description: String,
        val price: Float
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Service> = object : Parcelable.Creator<Service> {
            override fun createFromParcel(parcel: Parcel): Service = Service(parcel)
            override fun newArray(size: Int): Array<Service?> = arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readFloat())

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeString(description)
        parcel.writeFloat(price)
    }
}