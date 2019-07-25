package com.example.mangavinek.core.util.resource

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ResourceLiveData<T> : MutableLiveData<Resource<T>>() {

    fun observeResource(
            owner: LifecycleOwner,
            onSuccess: (T) -> Unit,
            onError: (Resource<T>) -> Unit) {

        observe(owner, Observer<Resource<T>> {
            if (it!!.status == Status.SUCCESS) {
                onSuccess.invoke(it.data!!)
            } else {
                onError.invoke(it)
            }
        })
    }
}