package com.example.mangavinek

import com.example.mangavinek.entity.ResponseList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("manga")
    fun obterMangas(): Deferred<ResponseList>
}