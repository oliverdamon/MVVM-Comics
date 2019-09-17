package com.example.mangavinek.feature.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.helper.PaginationConfig
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.feature.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel(), PaginationConfig {

    val getList = MutableLiveData<Resource<MutableList<HomeResponse>>>()
    var currentPage = 2
    var releasedLoad: Boolean = true

    fun fetchList(currentPage: Int = 1) {
        viewModelScope.launch {
            getList.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getList.success(repository.getList(HOME_URL_PAGINATION.plus(currentPage))?.let { it })
                }
            } catch (e: Exception) {
                getList.error(e)
            } finally {
                getList.loading(false)
            }
        }
    }

    override fun nextPage() {
        fetchList(currentPage++)
        releasedLoad = false
    }

    override fun backPreviousPage() {
        fetchList(currentPage-1)
    }

    override fun refreshViewModel() {
        currentPage = 2
        fetchList()
    }
}