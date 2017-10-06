package com.hamp.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.reflect.ParameterizedType

class BaseValueEventListener<Model, Entity>(
        private val mapper: FirebaseMapper<Entity, Model>,
        private val callback: FirebaseDatabaseRepositoryCallback<Model>
) : ValueEventListener {

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val data = mapper.map(dataSnapshot)
        callback.onSuccess(data)
    }

//    if (callback.callbackType == CallbackType.OBJECT) {
//        val data = mapper.map(dataSnapshot)
//        callback.onSuccess(data)
//    } else {
//        val data = mapper.mapList(dataSnapshot)
//        callback.onSuccess(data)
//    }

    override fun onCancelled(databaseError: DatabaseError) {
        callback.onError(databaseError.toException())
    }

    @Suppress("UNCHECKED_CAST")
    private fun getModelClass(): Class<Model> {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        return superclass.actualTypeArguments[0] as Class<Model>
    }
}