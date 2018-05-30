package com.hamp.data.db.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class HampService(
        @PrimaryKey
        val id: String,
        val name: String,
        val image: Int,
        val description: String,
        val price: Float
) : Parcelable {
        constructor(source: Parcel) : this(
                source.readString(),
                source.readString(),
                source.readInt(),
                source.readString(),
                source.readFloat()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeString(id)
                writeString(name)
                writeInt(image)
                writeString(description)
                writeFloat(price)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<HampService> = object : Parcelable.Creator<HampService> {
                        override fun createFromParcel(source: Parcel): HampService = HampService(source)
                        override fun newArray(size: Int): Array<HampService?> = arrayOfNulls(size)
                }
        }
}