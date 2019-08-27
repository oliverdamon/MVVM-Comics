package com.example.mangavinek.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.data.model.detail.entity.DetailResponse
import com.example.mangavinek.data.model.detail.entity.StatusChapter
import com.example.mangavinek.data.model.favorite.entity.Favorite
import com.example.mangavinek.detail.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: DetailRepository) : BaseViewModel() {
    val getDetail = MutableLiveData<Resource<DetailResponse>>()
    val getListDetailChapter = MutableLiveData<Resource<MutableList<DetailChapterResponse>>>()
    val mergeStatusList = MutableLiveData<MutableList<StatusChapter>>()

    fun fetchDetail(url: String) {
        viewModelScope.launch {
            getDetail.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getDetail.success(repository.getDetail(url)?.let { it })
                }
            } catch (e: Exception) {
                getDetail.error(e)
            } finally {
                getDetail.loading(false)
            }
        }
    }

    fun fetchList(url: String) {
        viewModelScope.launch {
            getListDetailChapter.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListDetailChapter.success(repository.getDetailChapter(url)?.let { it })
                }
            } catch (e: Exception) {
                getListDetailChapter.error(e)
            } finally {
                getListDetailChapter.loading(false)
            }
        }
    }

    fun fetchListStatus(detailResponse: DetailResponse) {
        mergeStatusList.value = repository.mergeStatusList(detailResponse)
    }

    fun insertAndRemoveComic(favorite: Favorite, title: String){
        if (searchTitle(title) > 0) {
            repository.removeComic(title)
        } else {
            repository.insertComic(favorite)
        }
    }

    fun searchTitle(title: String) = repository.buscaPorId(title)

}