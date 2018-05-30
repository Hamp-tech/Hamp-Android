package com.hamp.common

sealed class NetworkViewState {
    class Loading(val show: Boolean) : NetworkViewState()
    class Success<T>(val data: T) : NetworkViewState()
    class Error(val error: Any) : NetworkViewState()
}