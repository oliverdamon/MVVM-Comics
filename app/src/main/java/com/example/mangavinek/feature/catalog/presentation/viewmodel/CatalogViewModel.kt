package com.example.mangavinek.feature.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.PaginationConfig
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import timber.log.Timber

class CatalogViewModel(private val repository: CatalogRepository) : BaseViewModel(), AnkoLogger, PaginationConfig{

    val getListCatalog = MutableLiveData<Resource<MutableList<CatalogResponse>>>()
    var getLastPagination = MutableLiveData<Int>()
    var currentPage = 2
    var releasedLoad: Boolean = true
    var url: String = ""

    fun fetchList(url: String, page: Int = 1) {
        this.url = url

        viewModelScope.launch {
            getListCatalog.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListCatalog.success(repository.getListCatalog(url.plus(page))?.let { it })
                }
            } catch (e: Exception) {
                getListCatalog.error(e)
            } finally {
                getListCatalog.loading(false)
            }
            fetchLastPagination(url)
        }

    }

    private suspend fun fetchLastPagination(url: String) {
        try {
            withContext(Dispatchers.IO) {
                getLastPagination.postValue(repository.getLastPagination(url)?.let { it })
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    override fun nextPage() {
        fetchList(url, currentPage++)
        releasedLoad = false
    }

    override fun backPreviousPage() {
        fetchList(url,currentPage-1)
    }

    override fun refreshViewModel() {
        currentPage = 2
        fetchList(url)
    }
}