package com.hamp.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

class RealmMigrations : RealmMigration {
    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        val schema = realm?.schema

//        SAMPLE (https://github.com/budioktaviyan/realm-migration-sample)
//        if (oldVersion == 1) {
//            val userSchema = schema.get("UserData")
//            userSchema.addField("age", Int::class.javaPrimitiveType)
//        }

    }
}