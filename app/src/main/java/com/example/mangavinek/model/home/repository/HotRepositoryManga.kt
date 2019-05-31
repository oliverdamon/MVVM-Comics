package com.example.mangavinek.model.home.repository

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.api.ApiServiceSoup
import kotlinx.coroutines.*
import org.jsoup.select.Elements
import java.io.IOException

class HotRepositoryManga {

    private val dataModel: MutableLiveData<Resource<Elements>> = MutableLiveData()

    fun getListHot(url: String): MutableLiveData<Resource<Elements>> {
        CoroutineScope(Dispatchers.IO).launch {
            dataModel.postValue(Resource.Start())
            try {
                val links: Elements? = ApiServiceSoup.getListHot(url)
                withContext(Dispatchers.Main) {
                    links?.let {
                        dataModel.value = Resource.Success(it)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return dataModel
    }
}