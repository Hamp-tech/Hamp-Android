package com.hamp.domain

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var id = 1
    var identifier: String = ""
    var name: String = ""
    var surname: String = ""
    var email: String = ""
    @Ignore
    var password: String = ""
    var phone: String = ""
    var birthday: String = ""
    var gender: String = ""
    var tokenFCM: String = ""
    var language: String = ""
    var os: String = ""
    var stripeID: String = ""
    @Ignore
    var cards: List<String> = arrayListOf()
}