package com.example.mangavinek.catalog.model.domain.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class CatalogReponse(var element: Element){
    val title = element.select(".thead").text().toString()
    val image = element.select("img").attr("src").toString()
    val link = element.select("a").attr("href").toString()
}

fun Elements.getItems(): MutableList<CatalogReponse> {
    val transformedElements = mutableListOf<CatalogReponse>()
    this.mapNotNull { transformedElements.add(CatalogReponse(it)) }

    return transformedElements
}