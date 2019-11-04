package com.example.mangavinek.feature.detail.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.entity.favorite.FavoriteDB
import com.example.mangavinek.feature.detail.model.domain.DetailChapterDomain
import com.example.mangavinek.feature.detail.model.domain.DetailDomain
import com.example.mangavinek.feature.detail.model.domain.StatusChapterDomain
import com.example.mangavinek.feature.detail.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: DetailRepository) : BaseViewModel() {
    val getDetail = MutableLiveData<Resource<DetailDomain>>()
    val getListDetailChapter = MutableLiveData<Resource<List<DetailChapterDomain>>>()
    val getListDetailStatusChapter = MutableLiveData<MutableList<StatusChapterDomain>>()

    fun fetchDetail(url: String) {
        viewModelScope.launch {
            getDetail.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getDetail.success(repository.getDetail(url)?.let { it })
                    getListDetailStatusChapter.postValue(repository.getListDetailStatusChapter())
                }
            } catch (e: Exception) {
                getDetail.error(e)
            } finally {
                getDetail.loading(false)
            }
        }
    }

    fun fetchListDetailChapter(url: String) {
        viewModelScope.launch {
            getListDetailChapter.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListDetailChapter.success(repository.getListDetailChapter(url)?.let { it })
                }
            } catch (e: Exception) {
                getListDetailChapter.error(e)
            } finally {
                getListDetailChapter.loading(false)
            }
        }
    }

    fun insertComic(favoriteDB: FavoriteDB){
        repository.insertComic(favoriteDB)
    }

    fun removeComic(title: String){
        repository.removeComic(title)
    }

    fun searchTitle(title: String) = repository.searchForTitle(title)

}