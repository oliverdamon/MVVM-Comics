package com.example.mangavinek.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.core.helper.Resource

abstract class BaseViewModel : ViewModel() {

    protected fun <T> MutableLiveData<Resource<T>>.success(data: T?) {
        postValue(Resource.success(data))
    }

    protected fun <T> MutableLiveData<Resource<T>>.error(e: Exception?) {
        value = Resource.error(e)
    }

    protected fun <T> MutableLiveData<Resource<T>>.loading() {
        value = Resource.loading()
    }

}