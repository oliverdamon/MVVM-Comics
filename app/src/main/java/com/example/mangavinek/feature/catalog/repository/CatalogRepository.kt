package com.example.mangavinek.feature.catalog.repository

import com.example.mangavinek.core.util.alphaNumericOnly
import com.example.mangavinek.data.entity.catalog.CatalogResponse
import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import com.example.mangavinek.feature.catalog.model.mapper.CatalogMapper

class CatalogRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getListCatalog(url: String): List<CatalogDomain>? {
        val listCatalogResponse = CatalogResponse().addElements(apiServiceSoup.getListCatalog(url))
        return CatalogMapper.transformEntityToDomain(listCatalogResponse)
    }

    fun getLastPagination(url: String): Int?{
        return try {
            apiServiceSoup.getLastPagination(url).text().alphaNumericOnly()
        } catch (t: Throwable){
            null
        }
    }
}