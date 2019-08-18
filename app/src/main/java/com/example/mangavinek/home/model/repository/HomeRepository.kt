package com.example.mangavinek.home.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.home.model.domain.entity.HomeResponse
import com.example.mangavinek.home.model.domain.entity.getItems

class HomeRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getList(url: String): MutableList<HomeResponse>? {
        val response = apiServiceSoup.getListHot(url)
        return response.getItems()
    }
}