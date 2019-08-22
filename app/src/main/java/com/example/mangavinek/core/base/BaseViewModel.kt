package com.example.mangavinek.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.helper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    infix fun <T> MutableLiveData<Resource<T>>.addCallbackCoroutines(block: () -> T?) =
        viewModelScope.launch {
            value = Resource.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    postValue(Resource.success(block.invoke()))
                }
            } catch (e: Exception) {
                value = Resource.error(e)
            } finally {
                value = Resource.loading(false)
            }
        }

    infix fun <T> MutableLiveData<T>.addNotCallbackCoroutines(block: () -> T?) =
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    postValue(block.invoke())
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
}