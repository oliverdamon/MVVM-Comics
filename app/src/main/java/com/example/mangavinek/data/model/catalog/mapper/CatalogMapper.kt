package com.example.mangavinek.data.model.catalog.mapper

import com.example.mangavinek.feature.model.catalog.entity.CatalogResponse
import com.example.mangavinek.data.model.catalog.domain.CatalogDomain

object CatalogMapper {
    fun transformEntityToDomain(listCatalogResponse: List<CatalogResponse>): List<CatalogDomain> {
        val listCatalogDomain = ArrayList<CatalogDomain>()

        listCatalogResponse.forEach {
            val catalogDomain = CatalogDomain(
                title = it.title,
                image = it.image,
                url = it.url
            )
            listCatalogDomain.add(catalogDomain)
        }

        return listCatalogDomain
    }
}