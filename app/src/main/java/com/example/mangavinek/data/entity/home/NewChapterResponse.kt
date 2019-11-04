package com.example.mangavinek.data.entity.home

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class NewChapterResponse(element: Element? = null){
    val title = element?.select(".thead")?.text().toString()
    val image = element?.select("img")?.attr("src").toString()
    val url = element?.select("a.button")?.attr("href").toString()

    fun addElements(elements: Elements): List<NewChapterResponse> {
        val listElements = mutableListOf<NewChapterResponse>()
        elements.mapNotNull {
            listElements.add(NewChapterResponse(it))
        }

        return listElements
    }
}
