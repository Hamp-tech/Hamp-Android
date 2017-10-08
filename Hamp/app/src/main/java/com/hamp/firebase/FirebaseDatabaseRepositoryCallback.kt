package com.hamp.firebase

abstract class FirebaseDatabaseRepositoryCallback<in T>(val callbackType: CallbackType) {
//    lateinit var type: Any
//
//    init {
//        if (callbackType == CallbackType.OBJECT) type as T
//        else type as List<T>
//    }

    abstract fun onSuccess(result: T)
    //    abstract fun onSuccess(result: List<T>)
    abstract fun onError(e: Exception)
}

enum class CallbackType {
    LIST, OBJECT
}