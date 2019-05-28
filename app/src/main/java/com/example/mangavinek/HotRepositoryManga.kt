package com.example.mangavinek

import androidx.lifecycle.MutableLiveData
import com.example.mangavinek.entity.ResponseList
import kotlinx.coroutines.*
import org.jsoup.select.Elements
import java.io.IOException

class HotRepositoryManga {

    private val service = RetrofitFactory.getApiClient().obterMangas()
    private val newsData: MutableLiveData<Resource<ResponseList>> = MutableLiveData()
    private val dataModel: MutableLiveData<Resource<Elements>> = MutableLiveData()

    fun getNicePlaces(): MutableLiveData<Resource<ResponseList>> {
        newsData.postValue(Resource.Start())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: ResponseList? = service.await()
                withContext(Dispatchers.Main) {
                    response?.let { newsData.postValue(Resource.Success(it)) }
                }
            } catch (e: Throwable) {
                newsData.postValue(Resource.Error(e))
            }
        }

        return newsData
    }

    fun getListHot(url: String): MutableLiveData<Resource<Elements>> {
        CoroutineScope(Dispatchers.IO).launch {
            dataModel.postValue(Resource.Start())
            try {
                val links: Elements? = ApiServiceSoup.getList(url)
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