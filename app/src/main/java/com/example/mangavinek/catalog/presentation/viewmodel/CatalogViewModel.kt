package com.example.mangavinek.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import timber.log.Timber

class CatalogViewModel(private val repository: CatalogRepository) : BaseViewModel(), AnkoLogger {

    val getListCatalog = MutableLiveData<Resource<MutableList<CatalogResponse>>>()
    var getLastPagination = MutableLiveData<Int>()

    fun fetchList(url: String, page: Int = 1) {
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
}