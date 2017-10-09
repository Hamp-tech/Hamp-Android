package com.hamp.firebase

import com.google.firebase.database.DataSnapshot
import java.lang.reflect.ParameterizedType

abstract class FirebaseMapper<in Entity, out Model> : IMapper<Entity, Model> {

    fun map(dataSnapshot: DataSnapshot): Model {
        val entity = dataSnapshot.getValue(getEntityClass())
        return map(entity)
    }

    fun mapList(dataSnapshot: DataSnapshot): List<Model> {
        val list = arrayListOf<Model>()
        dataSnapshot.children.mapTo(list) { map(it) }
        return list
    }

    @Suppress("UNCHECKED_CAST")
    private fun getEntityClass(): Class<Entity> {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        return superclass.actualTypeArguments[0] as Class<Entity>
    }
}

