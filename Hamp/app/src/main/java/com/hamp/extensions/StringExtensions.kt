package com.hamp.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.fromJsonObject(): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, type)
}