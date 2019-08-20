package com.example.mangavinek.publishing.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.publishing.domain.PublishingDomain
import com.example.mangavinek.publishing.repository.PublishingRepository

class PublishingViewModel(private val repository: PublishingRepository) : ViewModel() {

    val getListPublishing = MutableLiveData<List<PublishingDomain>>()

    fun fetchList() {
        getListPublishing.value = repository.listPublishing()?.let { it }
    }

}