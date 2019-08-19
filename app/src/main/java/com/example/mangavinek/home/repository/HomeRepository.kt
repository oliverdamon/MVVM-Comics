package com.example.mangavinek.home.repository

import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.data.model.home.entity.HomeResponse
import com.example.mangavinek.data.model.home.entity.getItems

class HomeRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getList(url: String): MutableList<HomeResponse>? {
        val response = apiServiceSoup.getListHot(url)
        return response.getItems()
    }
}