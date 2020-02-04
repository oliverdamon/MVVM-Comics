package com.example.mangavinek.feature.publishing.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.feature.publishing.domain.PublishingDomain
import com.example.mangavinek.feature.publishing.repository.PublishingRepository

class PublishingViewModel(private val repository: PublishingRepository) : ViewModel() {

    private val mutableLiveDataListPublishing = MutableLiveData<List<PublishingDomain>>()

    init {
        fetchList()
    }

    val getLiveDataListPublishing: LiveData<List<PublishingDomain>>
        get() = mutableLiveDataListPublishing

    private fun fetchList() {
        mutableLiveDataListPublishing.value = repository.listPublishing()?.let { it }
    }
}