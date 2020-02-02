package com.example.mangavinek.feature.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import timber.log.Timber

class CatalogViewModel(var url: String, private val repository: CatalogRepository) : BaseViewModel(), AnkoLogger{

    val mutableLiveDataListCatalog = MutableLiveData<Resource<List<CatalogDomain>>>()
    val mutableLiveDataLastPagination = MutableLiveData<Int>()
    var currentPage = 2
    var releasedLoad: Boolean = true

    init {
        fetchListCatalog(url)
    }

    private fun fetchListCatalog(url: String, page: Int = 1) {
        this.url = url

        viewModelScope.launch {
            mutableLiveDataListCatalog.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataListCatalog.success(repository.getListCatalog(url.plus(page))?.let { it })
                }
            } catch (e: Exception) {
                mutableLiveDataListCatalog.error(e)
            }

            fetchLastPagination(url)
        }

    }

    private suspend fun fetchLastPagination(url: String) {
        try {
            withContext(Dispatchers.IO) {
                mutableLiveDataLastPagination.postValue(repository.getLastPagination(url)?.let { it })
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun nextPage() {
        fetchListCatalog(url, currentPage++)
        releasedLoad = false
    }

    fun backPreviousPage() {
        fetchListCatalog(url,currentPage-1)
    }

    fun refreshViewModel() {
        currentPage = 2
        fetchListCatalog(url)
    }
}