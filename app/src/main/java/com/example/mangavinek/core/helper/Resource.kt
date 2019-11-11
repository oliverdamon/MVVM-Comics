package com.example.mangavinek.core.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.Exception

data class Resource<out T>(val status: Status, val data: T?, val exception: Exception?) {

    enum class Status { SUCCESS, ERROR, LOADING }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}

fun <T> LiveData<Resource<T>>.observeResource(
    owner: LifecycleOwner,
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit,
    onLoading: () -> Unit) {

    observe(owner, Observer { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.let {
                    onSuccess.invoke(it)
                }
            }
            Resource.Status.ERROR -> {
                resource.exception?.let {
                    onError.invoke(it)
                }
            }
            Resource.Status.LOADING -> {
                onLoading.invoke()
            }
        }

    })
}