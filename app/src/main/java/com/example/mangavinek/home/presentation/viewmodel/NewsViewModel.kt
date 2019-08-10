package com.example.mangavinek.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.util.StateLiveData
import com.example.mangavinek.core.util.StateMutableLiveData
import com.example.mangavinek.home.model.domain.entity.Model
import com.example.mangavinek.home.model.repository.HotRepositoryManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val repository: HotRepositoryManga) : ViewModel() {

    private val state = StateMutableLiveData<MutableList<Model>, Throwable>()
    val getList: StateLiveData<MutableList<Model>, Throwable> get() = state

    fun fetchList(page: Int = 1) {
        viewModelScope.launch {
            state.loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    repository.getList(HOME_URL_PAGINATION.plus(page))?.let {
                        state.success.postValue(it)
                    }
                }
            } catch (t: Throwable) {
                state.error.value = t
            } catch (e: HttpException) {
            } finally {
                state.loading.value = false
            }
        }
    }


}