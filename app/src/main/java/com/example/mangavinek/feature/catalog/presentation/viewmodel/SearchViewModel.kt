package com.example.mangavinek.feature.catalog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.catalog.domain.CatalogDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repository: CatalogRepository) : BaseViewModel(){

    private val mutableLiveDataListSearch = MutableLiveData<Resource<List<CatalogDomain>>>()
    private val mutableLiveDataLastPagination = MutableLiveData<Int>()
    var url: String = ""
    var currentPage = 1
    var releasedLoad: Boolean = true
    var lastPage: Boolean = false

    val getLiveDataListSearch: LiveData<Resource<List<CatalogDomain>>>
        get() = mutableLiveDataListSearch

    val getLiveDataLastPagination: LiveData<Int>
        get() = mutableLiveDataLastPagination

    fun fetchListSearch(url: String, page: Int = 1) {
        this.url = url

        viewModelScope.launch {
            mutableLiveDataListSearch.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataLastPagination.postValue(repository.getLastPagination(url))
                    mutableLiveDataListSearch.success(repository.getListCatalog(url.plus(page))?.let { it })

                    resetPagingFlags()
                }
            } catch (e: Exception) {
                mutableLiveDataListSearch.error(e)
            }
        }
    }

    private fun resetPagingFlags(){
        releasedLoad = true
        lastPage = false
    }

    fun nextPage() {
        fetchListSearch(url, ++currentPage)
        releasedLoad = false
    }

    fun backPreviousPage() {
        fetchListSearch(url, currentPage)
    }

    fun refreshViewModel() {
        currentPage = 1
        fetchListSearch(url)
    }

    fun pagingFinished(){
        lastPage = true
    }
}