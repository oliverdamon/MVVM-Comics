package com.example.mangavinek.model.home.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class Model(var element: Element){
    val title = element.select(".thead").text().toString()
    val image = element.select("img").attr("src").toString()
    val link = element.select("a.button").attr("href").toString()
}

fun Elements.getItems(): MutableList<Model> {
    val transformedElements = mutableListOf<Model>()
    this.mapNotNull { transformedElements.add(Model(it)) }

    return transformedElements
}
