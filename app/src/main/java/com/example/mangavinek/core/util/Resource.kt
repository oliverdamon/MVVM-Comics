package com.example.mangavinek.core.util

import java.lang.Exception

data class Resource<out T>(val status: Status, val data: T?, val boolean: Boolean?, val exception: Exception?) {

    enum class Status { SUCCESS, ERROR, LOADING }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(exception: Exception?): Resource<T> {
            return Resource(Status.ERROR, null, null, exception)
        }

        fun <T> loading(boolean: Boolean?): Resource<T> {
            return Resource(Status.LOADING, null, boolean, null)
        }
    }
}