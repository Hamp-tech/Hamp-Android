package com.hamp.repository

import com.google.firebase.database.FirebaseDatabase

class UserRepository {

    var database = FirebaseDatabase.getInstance().getReference("users")


}

//class UserRepository : FirebaseDatabaseRepository<User>(UserMapper()) {
//    override fun getRootNode() = "users"
//}
//
//class UserMapper : FirebaseMapper<User, User>() {
//    override fun map(from: User) = from
//}
