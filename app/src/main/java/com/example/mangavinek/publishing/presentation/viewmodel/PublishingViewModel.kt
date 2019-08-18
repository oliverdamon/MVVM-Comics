package com.example.mangavinek.publishing.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.publishing.model.domain.mock.PublishingObject
import com.example.mangavinek.publishing.model.repository.PublishingRepository

class PublishingViewModel(private val repository: PublishingRepository) : ViewModel() {

    val getListPublishing = MutableLiveData<List<PublishingObject>>()

    fun fetchList() {
        getListPublishing.value = repository.listPublishing()?.let { it }
    }

}