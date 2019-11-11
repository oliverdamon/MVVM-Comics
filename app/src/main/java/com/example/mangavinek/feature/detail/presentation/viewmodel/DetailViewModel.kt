package com.example.mangavinek.feature.detail.presentation.viewmodel

import androidx.lifecycle.LiveData
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

class DetailViewModel(val url: String, private val repository: DetailRepository) : BaseViewModel() {
    private val mutableLiveDataDetail = MutableLiveData<Resource<DetailDomain>>()
    private val mutableLiveDataListDetailChapter = MutableLiveData<Resource<List<DetailChapterDomain>>>()
    private val mutableLiveDataListDetailStatusChapter = MutableLiveData<MutableList<StatusChapterDomain>>()

    val getDetail: LiveData<Resource<DetailDomain>> by lazy {
        fetchDetail(url)
        return@lazy mutableLiveDataDetail
    }

    val getListDetailStatusChapter: LiveData<MutableList<StatusChapterDomain>> by lazy {
        return@lazy mutableLiveDataListDetailStatusChapter
    }

    val getListDetailChapter: LiveData<Resource<List<DetailChapterDomain>>> by lazy {
        fetchListDetailChapter(url)
        return@lazy mutableLiveDataListDetailChapter
    }

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

    fun insertComic(favoriteDB: FavoriteDB){
        repository.insertComic(favoriteDB)
    }

    fun removeComic(title: String){
        repository.removeComic(title)
    }

    fun searchTitle(title: String) = repository.searchForTitle(title)

}