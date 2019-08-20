package com.example.mangavinek.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.catalog.repository.CatalogRepository
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import timber.log.Timber

class CatalogViewModel(private val repository: CatalogRepository) : ViewModel(), AnkoLogger {

    val getListCatalog = MutableLiveData<Resource<MutableList<CatalogResponse>>>()
    var getLastPagination = MutableLiveData<Int>()

    fun fetchList(url: String, page: Int = 1) {

        viewModelScope.launch {
            getListCatalog.value = Resource.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListCatalog.postValue(repository.getListCatalog(url.plus(page))?.let { Resource.success(it) })
                }
            } catch (e: Exception) {
                getListCatalog.value = Resource.error(e)
            } finally {
                getListCatalog.value = Resource.loading(false)
            }
            fetchLastPagination(url)
        }
    }

    private suspend fun fetchLastPagination(url: String) {
        withContext(Dispatchers.IO) {
            try {
                getLastPagination.postValue(repository.getLastPagination(url)?.let { it })
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }


}