package com.example.mangavinek.feature.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.home.repository.HomeRepository
import com.example.mangavinek.data.model.home.domain.NewChapterDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    private val mutableLiveDataListNewChapter = MutableLiveData<Resource<List<NewChapterDomain>>>()
    var currentPage = 1
    var releasedLoad: Boolean = true

    init {
        fetchListNewChapter()
    }

    val getLiveDataListNewChapter: LiveData<Resource<List<NewChapterDomain>>>
        get() = mutableLiveDataListNewChapter

    private fun fetchListNewChapter(currentPage: Int = 1) {
        mutableLiveDataListNewChapter.loading()

        viewModelScope.launchWithCallback(
            onSuccess = {
                withContext(Dispatchers.IO) {
                    mutableLiveDataListNewChapter.success(repository.getComics(HOME_URL_PAGINATION.plus(currentPage)).let { it })
                    releasedLoad = true
                }
            },
            onError = {
                mutableLiveDataListNewChapter.error(it)
            })
    }

    fun nextPage() {
        fetchListNewChapter(++currentPage)
        releasedLoad = false
    }

    fun backPreviousPage() {
        fetchListNewChapter(currentPage)
    }

    fun refreshViewModel() {
        currentPage = 1
        fetchListNewChapter()
    }
}