package com.example.mangavinek.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.catalog.repository.CatalogRepository
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import org.jetbrains.anko.AnkoLogger

class CatalogViewModel(private val repository: CatalogRepository) : BaseViewModel(), AnkoLogger {

    val getListCatalog = MutableLiveData<Resource<MutableList<CatalogResponse>>>()
    var getLastPagination = MutableLiveData<Int>()

    fun fetchList(url: String, page: Int = 1) {
        getListCatalog.addCallbackCoroutines {
            repository.getListCatalog(url.plus(page))
        }

        fetchLastPagination(url)
    }

    private fun fetchLastPagination(url: String) {
        getLastPagination.addNotCallbackCoroutines { repository.getLastPagination(url) }
    }
}