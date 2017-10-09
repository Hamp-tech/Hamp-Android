package com.hamp.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {

    private var database = FirebaseDatabase.getInstance().getReference("users")
    private var listener: ValueEventListener? = null

    init {
        database.child("users").child("882EbJY7fOd6KDySu8MFtXq2mvh1")
    }

    fun addListener(valueEventListener: ValueEventListener) {
        listener = valueEventListener
        database.addValueEventListener(listener)
    }

    fun removeListener() {
        database.removeEventListener(listener)
    }
}

//class UserRepository : FirebaseDatabaseRepository<User>(UserMapper()) {
//    override fun getRootNode() = "users"
//}
//
//class UserMapper : FirebaseMapper<User, User>() {
//    override fun map(from: User) = from
//}
