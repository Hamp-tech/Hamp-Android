package com.hamp.data.db.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.hamp.domain.Card

@Entity
data class User(
        @PrimaryKey
        var id: Int = 1,
        var identifier: String? = null,
        var name: String = "",
        var surname: String = "",
        var email: String? = null,
        @Ignore
        var password: String? = null,
        var phone: String = "",
        var birthday: String = "",
        var gender: String = "",
        var tokenFCM: String? = null,
        var language: String = "",
        var os: String = "",
        var stripeID: String? = null,
        @Ignore
        var cards: List<Card>? = null
)