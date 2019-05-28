package com.example.mangavinek

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jsoup.select.Elements

class NewsViewModel : ViewModel() {

    var mutableLiveDataHot: MutableLiveData<Resource<Elements>>? = null
    var newsRepository: HotRepositoryManga? = HotRepositoryManga()

    init {
        mutableLiveDataHot = newsRepository?.getListHot("http://soquadrinhos.com/forumdisplay.php?fid=2")
    }

    fun reload(url: String){
        mutableLiveDataHot = newsRepository?.getListHot(url)
    }

}