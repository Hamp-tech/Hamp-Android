package com.hamp.domain

import android.os.Parcel
import android.os.Parcelable

data class Basket(
        var services: MutableList<ServiceQuantity> = arrayListOf()
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Basket> = object : Parcelable.Creator<Basket> {
            override fun createFromParcel(source: Parcel): Basket = Basket(source)
            override fun newArray(size: Int): Array<Basket?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(ServiceQuantity.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(services)
    }
}