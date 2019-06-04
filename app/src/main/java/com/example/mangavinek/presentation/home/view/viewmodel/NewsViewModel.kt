package com.example.mangavinek.presentation.home.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.core.constant.Constant
import com.example.mangavinek.model.home.repository.HotRepositoryManga
import com.example.mangavinek.core.util.Resource
import org.jsoup.select.Elements

class NewsViewModel : ViewModel() {

    var mutableLiveDataHot: MutableLiveData<Resource<Elements>>? = null
    var newsRepository: HotRepositoryManga? =
        HotRepositoryManga()

    fun init(page: Int = 1){
        mutableLiveDataHot = newsRepository?.getListHot(Constant.HOME_URL_PAGINATION.plus(page))
    }

}