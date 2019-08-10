package com.example.mangavinek.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.example.mangavinek.core.util.StateLiveData
import com.example.mangavinek.core.util.StateMutableLiveData
import com.example.mangavinek.detail.model.domain.entity.DetailChapterResponse
import com.example.mangavinek.detail.model.domain.entity.DetailResponse
import com.example.mangavinek.detail.model.repository.DetailRepository
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
                    repository.getDetail(url)?.let {
                        stateDetail.success.postValue(it)
                    }
                }
            } catch (t: Throwable) {
                stateDetail.error.value = t
            } catch (e: HttpException) {
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
                    repository.getDetailChapter(url)?.let {
                        stateDetailChapter.success.postValue(it)
                    }
                }
            } catch (t: Throwable) {
                stateDetailChapter.error.value = t
            } catch (e: HttpException) {
            } finally {
                stateDetailChapter.loading.value = false
            }
        }
    }
}