package com.example.mangavinek.home.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.home.model.domain.entity.Model
import com.example.mangavinek.home.model.domain.entity.getItems

class HotRepositoryManga(private val apiServiceSoup: ApiServiceSoup) {

    fun getList(url: String): MutableList<Model>? {
        val movieResponse = apiServiceSoup.getListHot(url)
        return movieResponse.getItems()
    }
}