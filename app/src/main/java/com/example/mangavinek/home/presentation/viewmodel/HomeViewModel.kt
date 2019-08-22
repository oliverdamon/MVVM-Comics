package com.example.mangavinek.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.core.constant.HOME_URL_PAGINATION
import com.example.mangavinek.core.helper.Resource
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.home.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    val getList = MutableLiveData<Resource<MutableList<HomeResponse>>>()

    fun fetchList(page: Int = 1) {
        getList.addCallbackCoroutines { repository.getList(HOME_URL_PAGINATION.plus(page)) }
    }

}