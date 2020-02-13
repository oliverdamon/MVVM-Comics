package com.example.mangavinek.feature.catalog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatalogViewModel(var url: String, private val repository: CatalogRepository) : BaseViewModel(){

    private val mutableLiveDataListCatalog = MutableLiveData<Resource<List<CatalogDomain>>>()
    private val mutableLiveDataLastPagination = MutableLiveData<Int>()
    var currentPage = 1
    var releasedLoad: Boolean = true
    var lastPage: Boolean = false

    init {
        fetchListCatalog(url)
    }

    val getLiveDataListCatalog: LiveData<Resource<List<CatalogDomain>>>
        get() = mutableLiveDataListCatalog

    val getLiveDataLastPagination: LiveData<Int>
        get() = mutableLiveDataLastPagination

    private fun fetchListCatalog(url: String, page: Int = 1) {
        this.url = url

        viewModelScope.launch {
            mutableLiveDataListCatalog.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataLastPagination.postValue(repository.getLastPagination(url))
                    mutableLiveDataListCatalog.success(repository.getListCatalog(url.plus(page))?.let { it })

                    resetPagingFlags()
                }
            } catch (e: Exception) {
                mutableLiveDataListCatalog.error(e)
            }
        }
    }

    private fun resetPagingFlags(){
        releasedLoad = true
        lastPage = false
    }

    fun nextPage() {
        fetchListCatalog(url, ++currentPage)
        releasedLoad = false
    }

    fun backPreviousPage() {
        fetchListCatalog(url, currentPage)
    }

    fun refreshViewModel() {
        currentPage = 1
        fetchListCatalog(url)
    }

    fun pagingFinished(){
        lastPage = true
    }
}