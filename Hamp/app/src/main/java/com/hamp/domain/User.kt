package com.hamp.domain

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var id: Long = -1
    var name: String = ""
    var surname: String = ""
    var mail: String = ""
    var password: String = ""
    var phone: String = ""
    var birthday: String = ""
    var gender: Int = -1
    var tokenFCM: String = ""
    var language: String = ""
    var os: String = ""
    var signupDate: String = ""
    var lastActivity: String = ""
    var unsubscribed: Boolean = false
    var stripeID: String = ""
}