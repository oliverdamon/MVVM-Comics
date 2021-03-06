package com.example.mangavinek.feature.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.data.model.detail.domain.DetailChapterDomain
import com.example.mangavinek.data.model.detail.domain.DetailDomain
import com.example.mangavinek.data.model.detail.domain.StatusChapterDomain
import com.example.mangavinek.feature.detail.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(val url: String, private val repository: DetailRepository) : BaseViewModel() {
    private val mutableLiveDataDetail = MutableLiveData<Resource<DetailDomain>>()
    private val mutableLiveDataListDetailChapter = MutableLiveData<Resource<List<DetailChapterDomain>>>()
    private val mutableLiveDataListDetailStatusChapter = MutableLiveData<MutableList<StatusChapterDomain>>()
    var getLiveDataFavorite: LiveData<FavoriteDB?>? = null

    init {
        fetchDetail(url)
        fetchListDetailChapter(url)
    }

    val getLiveDataDetail: LiveData<Resource<DetailDomain>>
        get() = mutableLiveDataDetail

    val getLiveDataListDetailChapter: LiveData<Resource<List<DetailChapterDomain>>>
        get() = mutableLiveDataListDetailChapter

    val getLiveDataListDetailStatusChapter: LiveData<MutableList<StatusChapterDomain>>
        get() = mutableLiveDataListDetailStatusChapter

    fun fetchDetail(url: String) {
        viewModelScope.launch {
            mutableLiveDataDetail.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataDetail.success(repository.getDetail(url)?.let { it })
                    mutableLiveDataListDetailStatusChapter.postValue(repository.getListDetailStatusChapter())
                }
            } catch (e: Exception) {
                mutableLiveDataDetail.error(e)
            }
        }
    }

    fun fetchListDetailChapter(url: String) {
        viewModelScope.launch {
            mutableLiveDataListDetailChapter.loading()
            try {
                withContext(Dispatchers.IO) {
                    mutableLiveDataListDetailChapter.success(repository.getListDetailChapter(url)?.let { it })
                }
            } catch (e: Exception) {
                mutableLiveDataListDetailChapter.error(e)
            }
        }
    }

    fun insertOrRemoveComic(insertObjectEnabled: Boolean, favoriteDB: FavoriteDB) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (insertObjectEnabled) {
                    repository.removeComic(favoriteDB)
                } else {
                    repository.insertComic(favoriteDB)
                }
            }
        }
    }

    fun findByTitle(title: String) {
        getLiveDataFavorite = repository.findByTitle(title)
    }
}