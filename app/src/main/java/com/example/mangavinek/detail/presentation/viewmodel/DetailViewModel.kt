package com.example.mangavinek.detail.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.util.StateLiveData
import com.example.mangavinek.core.util.StateMutableLiveData
import com.example.mangavinek.data.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.data.model.detail.entity.DetailResponse
import com.example.mangavinek.data.model.detail.entity.StatusChapter
import com.example.mangavinek.detail.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: DetailRepository) : ViewModel() {
    private val stateDetail = StateMutableLiveData<DetailResponse, Throwable>()
    val getDetail: StateLiveData<DetailResponse, Throwable> get() = stateDetail

    fun fetchDetail(url: String) {
        viewModelScope.launch {
            stateDetail.loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    stateDetail.success.postValue(repository.getDetail(url)?.let { it })
                }
            } catch (t: Throwable) {
                stateDetail.error.value = t
            } finally {
                stateDetail.loading.value = false
            }
        }
    }

    private val stateDetailChapter = StateMutableLiveData<MutableList<DetailChapterResponse>, Throwable>()
    val getListDetailChapter: StateLiveData<MutableList<DetailChapterResponse>, Throwable> get() = stateDetailChapter

    fun fetchList(url: String) {
        viewModelScope.launch {
            stateDetailChapter.loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    stateDetailChapter.success.postValue(repository.getDetailChapter(url)?.let { it })
                }
            } catch (t: Throwable) {
                stateDetailChapter.error.value = t
            } finally {
                stateDetailChapter.loading.value = false
            }
        }
    }

    val mergeStatusList = MutableLiveData<MutableList<StatusChapter>>()

    fun fetchListStatus(detailResponse: DetailResponse) {
        mergeStatusList.value = repository.mergeStatusList(detailResponse)
    }
}