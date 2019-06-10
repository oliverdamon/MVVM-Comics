package com.example.mangavinek.model.detail.repository

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.api.ApiServiceSoup
import com.example.mangavinek.core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.select.Elements
import java.io.IOException

class DetailRepository {
    private val dataModel: MutableLiveData<Resource<Elements>> = MutableLiveData()

    fun getDetail(url: String): MutableLiveData<Resource<Elements>> {
        CoroutineScope(Dispatchers.IO).launch {
            dataModel.postValue(Resource.Start())
            try {
                val response: Elements? = ApiServiceSoup.getDetail(url)
                withContext(Dispatchers.Main) {
                    response?.let {
                        dataModel.value = Resource.Success(it)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return dataModel
    }

    fun getDetailChapter(url: String): MutableLiveData<Resource<Elements>> {
        CoroutineScope(Dispatchers.IO).launch {
            dataModel.postValue(Resource.Start())
            try {
                val response: Elements? = ApiServiceSoup.getDetailChapter(url)
                withContext(Dispatchers.Main) {
                    response?.let {
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