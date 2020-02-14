package com.example.mangavinek.feature.catalog.model.mapper

import com.example.mangavinek.feature.model.catalog.entity.CatalogResponse
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain

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