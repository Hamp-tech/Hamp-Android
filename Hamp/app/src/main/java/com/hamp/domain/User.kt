package com.hamp.domain

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var id = 1
    var identifier: String? = null
    var name: String = ""
    var surname: String = ""
    var email: String? = null
    @Ignore
    var password: String? = null
    var phone: String = ""
    var birthday: String = ""
    var gender: String = ""
    var tokenFCM: String? = null
    var language: String = ""
    var os: String = ""
    var stripeID: String? = null
    @Ignore
    var cards: List<Card>? = null
}