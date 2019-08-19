package com.example.mangavinek.data.model.home.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class HomeResponse(var element: Element){
    val title = element.select(".thead").text().toString()
    val image = element.select("img").attr("src").toString()
    val link = element.select("a.button").attr("href").toString()
}

fun Elements.getItems(): MutableList<HomeResponse> {
    val transformedElements = mutableListOf<HomeResponse>()
    this.mapNotNull { transformedElements.add(HomeResponse(it)) }

    return transformedElements
}
