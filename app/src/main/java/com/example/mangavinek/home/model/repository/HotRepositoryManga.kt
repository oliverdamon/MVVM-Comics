package com.example.mangavinek.home.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.home.model.domain.entity.Model
import com.example.mangavinek.home.model.domain.entity.getItems

class HotRepositoryManga {

    fun getList(url: String): MutableList<Model>? {
        val movieResponse = ApiServiceSoup.getListHot(url)
        return movieResponse.getItems()
    }
}