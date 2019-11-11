package com.example.mangavinek.feature.catalog.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.PaginationConfig
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import timber.log.Timber

class SearchViewModel(private val repository: CatalogRepository) : BaseViewModel(), AnkoLogger, PaginationConfig{

    private val mutableLiveDataListSearch = MutableLiveData<Resource<List<CatalogDomain>>>()
    private var mutableLiveDataLastPagination = MutableLiveData<Int>()
    var url: String = ""
    var currentPage = 2
    var releasedLoad: Boolean = true

    val getListSearch: LiveData<Resource<List<CatalogDomain>>> by lazy {
        return@lazy mutableLiveDataListSearch
    }

    val getLastPagination: LiveData<Int> by lazy {
        return@lazy mutableLiveDataLastPagination
    }

    fun fetchListSearch(url: String, page: Int = 1) {
        this.url = url

        viewModelScope.launch {
            mutableLiveDataListSearch.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataListSearch.success(repository.getListCatalog(url.plus(page))?.let { it })
                }
            } catch (e: Exception) {
                mutableLiveDataListSearch.error(e)
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

    override fun nextPage() {
        fetchListSearch(url, currentPage++)
        releasedLoad = false
    }

    override fun backPreviousPage() {
        fetchListSearch(url,currentPage-1)
    }

    override fun refreshViewModel() {
        currentPage = 2
        fetchListSearch(url)
    }
}