package com.example.mangavinek.core.util

data class Resource<out T>(val status: Status, val data: T?, val boolean: Boolean?, val throwable: Throwable?) {

    enum class Status { SUCCESS, ERROR, LOADING }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(throwable: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, null, throwable)
        }

        fun <T> loading(boolean: Boolean?): Resource<T> {
            return Resource(Status.LOADING, null, boolean, null)
        }
    }
}