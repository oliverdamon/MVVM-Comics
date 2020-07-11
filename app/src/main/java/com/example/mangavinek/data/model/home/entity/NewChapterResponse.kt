package com.example.mangavinek.data.model.home.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class NewChapterResponse(element: Element? = null){
    var title = element?.select(".thead")?.text().toString()
    var image = element?.select("img")?.attr("src").toString()
    var url = element?.select("a.button")?.attr("abs:href").toString()

    fun addElements(elements: Elements): List<NewChapterResponse> {
        val listElements = mutableListOf<NewChapterResponse>()
        elements.mapNotNull {
            listElements.add(NewChapterResponse(it))
        }

        return listElements
    }
}
