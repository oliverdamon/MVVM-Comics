package com.example.mangavinek.detail.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.data.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.data.model.detail.entity.DetailResponse
import com.example.mangavinek.data.model.detail.entity.StatusChapter
import com.example.mangavinek.detail.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: DetailRepository) : ViewModel() {
    val getDetail = MutableLiveData<Resource<DetailResponse>>()

    fun fetchDetail(url: String) {

        viewModelScope.launch {
            getDetail.value = Resource.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getDetail.postValue(repository.getDetail(url)?.let { Resource.success(it) })
                }
            } catch (t: Throwable) {
                getDetail.value = Resource.error(t)
            } finally {
                getDetail.value = Resource.loading(false)
            }
        }
    }

    val getListDetailChapter = MutableLiveData<Resource<MutableList<DetailChapterResponse>>>()

    fun fetchList(url: String) {

        viewModelScope.launch {
            getListDetailChapter.value = Resource.loading(true)
            try {
                withContext(Dispatchers.IO) {
                    getListDetailChapter.postValue(repository.getDetailChapter(url)?.let { Resource.success(it) })
                }
            } catch (t: Throwable) {
                getListDetailChapter.value = Resource.error(t)
            } finally {
                getListDetailChapter.value = Resource.loading(false)
            }
        }
    }

    val mergeStatusList = MutableLiveData<MutableList<StatusChapter>>()

    fun fetchListStatus(detailResponse: DetailResponse) {
        mergeStatusList.value = repository.mergeStatusList(detailResponse)
    }
}