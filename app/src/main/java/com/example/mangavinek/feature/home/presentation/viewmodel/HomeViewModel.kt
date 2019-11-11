package com.example.mangavinek.feature.home.presentation.viewmodel

import androidx.lifecycle.LiveData
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

    private val mutableLiveDataListNewChapter = MutableLiveData<Resource<List<NewChapterDomain>>>()
    var currentPage = 2
    var releasedLoad: Boolean = true

    val getListNewChapter: LiveData<Resource<List<NewChapterDomain>>> by lazy {
        fetchListNewChapter()
        return@lazy mutableLiveDataListNewChapter
    }

    private fun fetchListNewChapter(currentPage: Int = 1) {
        viewModelScope.launch {
            mutableLiveDataListNewChapter.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataListNewChapter.success(repository.getListNewChapter(HOME_URL_PAGINATION.plus(currentPage)).let { it })
                }
            } catch (e: Exception) {
                mutableLiveDataListNewChapter.error(e)
            }
        }
    }

    override fun nextPage() {
        fetchListNewChapter(currentPage++)
        releasedLoad = false
    }

    override fun backPreviousPage() {
        fetchListNewChapter(currentPage-1)
    }

    override fun refreshViewModel() {
        currentPage = 2
        fetchListNewChapter()
    }
}