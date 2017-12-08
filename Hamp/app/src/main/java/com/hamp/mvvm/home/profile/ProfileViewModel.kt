package com.hamp.mvvm.home.profile

import android.arch.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

//    private var user: MutableLiveData<User>? = null
//    private val repository = UserRepository()

//    fun getUser(): LiveData<User> {
//        if (user == null) {
//            user = MutableLiveData()
//            loadUser()
//        }
//
//        return user as MutableLiveData<User>
//    }

//    private fun loadUser() {
//        repository.addListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val user = dataSnapshot.getValue(User::class.java)
//                Log.d("HOLA", user.toString())
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("HOLA", error.message)
//            }
//        })
//    }

//    override fun onCleared() {
//        repository.removeListener()
//    }
}
