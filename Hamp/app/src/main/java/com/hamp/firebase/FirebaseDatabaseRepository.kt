package com.hamp.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class FirebaseDatabaseRepository<Model>(private val mapper: FirebaseMapper) {

    protected var databaseReference: DatabaseReference
    protected var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>? = null
    private var listener: BaseValueEventListener? = null

    protected abstract fun getRootNode(): String

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(getRootNode())
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>) {
        this.firebaseCallback = firebaseCallback
        listener = BaseValueEventListener(mapper, firebaseCallback)
        databaseReference.addValueEventListener(listener)
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener)
    }
}