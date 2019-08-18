package com.example.mangavinek.catalog.model.domain.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class CatalogResponse(var element: Element){
    val title = element.select(".thead").text().toString()
    val image = element.select("img").attr("src").toString()
    val link = element.select("a").attr("href").toString()
}

fun Elements.getItems(): MutableList<CatalogResponse> {
    val transformedElements = mutableListOf<CatalogResponse>()
    val transformedElementsFilter = mutableListOf<CatalogResponse>()

    this.mapNotNull {
        transformedElements.add(CatalogResponse(it))
    }

    transformedElements.forEach {
        if (it.title != "Detalhes") {
            transformedElementsFilter.add(it)
        }
    }

    return transformedElementsFilter
}