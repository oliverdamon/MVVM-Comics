package com.example.mangavinek.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    val getList = MutableLiveData<Resource<MutableList<HomeResponse>>>()

    fun fetchList(page: Int = 1) {
        viewModelScope.launch {
            getList.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getList.success(repository.getList(HOME_URL_PAGINATION.plus(page))?.let { it })
                }
            } catch (e: Exception) {
                getList.error(e)
            } finally {
                getList.loading(false)
            }
        }
    }

}