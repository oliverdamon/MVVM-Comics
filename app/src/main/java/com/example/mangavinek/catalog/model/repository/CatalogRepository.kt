package com.example.mangavinek.catalog.model.repository

import com.example.mangavinek.catalog.model.domain.entity.CatalogReponse
import com.example.mangavinek.catalog.model.domain.entity.getItems
import com.example.mangavinek.core.api.ApiServiceSoup

class CatalogRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getListCatalog(url: String): MutableList<CatalogReponse>? {
        val movieResponse = apiServiceSoup.getListCatalog(url)
        return movieResponse.getItems()
    }

    fun getLastPagination(url: String): Int?{
        return apiServiceSoup.getCatalogLastPagination(url)[0].text().toInt()
    }
}