package com.example.mangavinek

sealed class Resource<T> {
    class Start<T> : Resource<T>()
    class Success<T>(var data: T) : Resource<T>()
    class Error<T>(val error: Throwable) : Resource<T>()
}