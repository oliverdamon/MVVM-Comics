package com.example.mangavinek.presentation.detail.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.detail.repository.DetailRepository
import org.jsoup.select.Elements

class DetailViewModel : ViewModel() {
    var mutableLiveData: MutableLiveData<Resource<Elements>>? = null
    var mutableLiveDataChapter: MutableLiveData<Resource<Elements>>? = null
    var detailRepository: DetailRepository? = DetailRepository()

    fun init(url: String){
        mutableLiveData = detailRepository?.getDetail(url)
    }

    fun init2(url: String){
        mutableLiveDataChapter = detailRepository?.getDetailChapter(url)
    }
}