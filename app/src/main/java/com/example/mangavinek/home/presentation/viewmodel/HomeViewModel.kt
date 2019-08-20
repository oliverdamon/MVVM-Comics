package com.example.mangavinek.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val getList = MutableLiveData<Resource<MutableList<HomeResponse>>>()

    fun fetchList(page: Int = 1) {
        viewModelScope.launch {
            getList.value = Resource.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getList.postValue(repository.getList(HOME_URL_PAGINATION.plus(page))?.let { Resource.success(it) })
                }
            } catch (e: Exception) {
                getList.value = Resource.error(e)
            } finally {
                getList.value = Resource.loading(false)
            }
        }
    }
}