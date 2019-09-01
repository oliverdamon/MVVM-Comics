package com.example.mangavinek.feature.catalog.repository

import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import com.example.mangavinek.data.model.catalog.entity.getItems
import com.example.mangavinek.data.source.remote.ApiServiceSoup

class CatalogRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getListCatalog(url: String): MutableList<CatalogResponse>? {
        val response = apiServiceSoup.getListCatalog(url)
        return response.getItems()
    }

    fun getLastPagination(url: String): Int?{
        return apiServiceSoup.getCatalogLastPagination(url)[0].text().toInt()
    }
}