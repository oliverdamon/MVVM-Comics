package com.example.mangavinek.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.util.StateLiveData
import com.example.mangavinek.core.util.StateMutableLiveData
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val state = StateMutableLiveData<MutableList<HomeResponse>, Throwable>()
    val getList: StateLiveData<MutableList<HomeResponse>, Throwable> get() = state

    fun fetchList(page: Int = 1) {
        viewModelScope.launch {
            state.loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    state.success.postValue(repository.getList(HOME_URL_PAGINATION.plus(page))?.let { it })
                }
            } catch (t: Throwable) {
                state.error.value = t
            } finally {
                state.loading.value = false
            }
        }
    }


}