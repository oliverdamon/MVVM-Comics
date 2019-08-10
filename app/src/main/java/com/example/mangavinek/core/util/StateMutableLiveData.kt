package com.example.mangavinek.core.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface StateLiveData<R, E> {
    fun observe(
        owner: LifecycleOwner,
        onSuccess: (R) -> Unit,
        onError: (E) -> Unit = {},
        onLoading: (Boolean) -> Unit = {}
    )

    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<R>
    val errorLiveData: LiveData<E>
}

class StateMutableLiveData<R, E> : StateLiveData<R, E> {
    val success = MutableLiveData<R>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<E>()

    override val loadingLiveData: LiveData<Boolean> get() = loading
    override val successLiveData: LiveData<R> get() = success
    override val errorLiveData: LiveData<E> get() = error

    override fun observe(
        owner: LifecycleOwner,
        onSuccess: (R) -> Unit,
        onError: (E) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        successLiveData.observe(owner, Observer {
            onSuccess.invoke(it)
        })

        loadingLiveData.observe(owner, Observer {
            onLoading.invoke(it)
        })

        errorLiveData.observe(owner, Observer {
            onError.invoke(it)
        })
    }
}