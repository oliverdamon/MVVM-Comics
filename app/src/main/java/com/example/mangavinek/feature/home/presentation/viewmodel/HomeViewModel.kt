package com.example.mangavinek.feature.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.helper.PaginationConfig
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import com.example.mangavinek.feature.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel(), PaginationConfig {

    val getListNewChapterDomain = MutableLiveData<Resource<List<NewChapterDomain>>>()
    var currentPage = 2
    var releasedLoad: Boolean = true

    fun fetchListNewChapterDomain(currentPage: Int = 1) {
        viewModelScope.launch {
            getListNewChapterDomain.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListNewChapterDomain.success(repository.getListNewChapterDomain(HOME_URL_PAGINATION.plus(currentPage)).let { it })
                }
            } catch (e: Exception) {
                getListNewChapterDomain.error(e)
            } finally {
                getListNewChapterDomain.loading(false)
            }
        }
    }

    override fun nextPage() {
        fetchListNewChapterDomain(currentPage++)
        releasedLoad = false
    }

    override fun backPreviousPage() {
        fetchListNewChapterDomain(currentPage-1)
    }

    override fun refreshViewModel() {
        currentPage = 2
        fetchListNewChapterDomain()
    }
}