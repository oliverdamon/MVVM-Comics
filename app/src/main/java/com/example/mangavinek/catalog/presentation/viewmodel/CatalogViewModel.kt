package com.example.mangavinek.catalog.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.example.mangavinek.catalog.model.domain.entity.CatalogReponse
import com.example.mangavinek.catalog.model.repository.CatalogRepository
import com.example.mangavinek.core.util.StateLiveData
import com.example.mangavinek.core.util.StateMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger

class CatalogViewModel(private val repository: CatalogRepository) : ViewModel(), AnkoLogger {

    private val state = StateMutableLiveData<MutableList<CatalogReponse>, Throwable>()
    val getListCatalog: StateLiveData<MutableList<CatalogReponse>, Throwable> get() = state
    var getLastPagination = MutableLiveData<Int>()

    fun fetchList(url: String, page: Int = 1) {
        viewModelScope.launch {
            state.loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    state.success.postValue(repository.getListCatalog(url.plus(page))?.let { it })
                }
            } catch (t: Throwable) {
                state.error.value = t
            } catch (e: HttpException) {
            } finally {
                state.loading.value = false
            }

            withContext(Dispatchers.IO) {
                try {
                    getLastPagination.postValue(repository.getLastPagination(url)?.let { it })
                } catch (t: Throwable) {
                }
            }
        }
    }


}