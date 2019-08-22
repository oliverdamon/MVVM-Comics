package com.example.mangavinek.detail.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.data.model.detail.entity.DetailResponse
import com.example.mangavinek.data.model.detail.entity.StatusChapter
import com.example.mangavinek.detail.repository.DetailRepository

class DetailViewModel(private val repository: DetailRepository) : BaseViewModel() {
    val getDetail = MutableLiveData<Resource<DetailResponse>>()
    val getListDetailChapter = MutableLiveData<Resource<MutableList<DetailChapterResponse>>>()
    val mergeStatusList = MutableLiveData<MutableList<StatusChapter>>()

    fun fetchDetail(url: String) {
        getDetail.addCallbackCoroutines { repository.getDetail(url) }
    }

    fun fetchList(url: String) {
        getListDetailChapter.addCallbackCoroutines { repository.getDetailChapter(url) }
    }

    fun fetchListStatus(detailResponse: DetailResponse) {
        mergeStatusList.value = repository.mergeStatusList(detailResponse)
    }
}