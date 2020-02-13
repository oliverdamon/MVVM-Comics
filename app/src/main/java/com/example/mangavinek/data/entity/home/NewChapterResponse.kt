package com.example.mangavinek.data.entity.home

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@Entity(tableName = "lancamentos")
class NewChapterResponse(element: Element? = null){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title = element?.select(".thead")?.text().toString()
    var image = element?.select("img")?.attr("src").toString()
    var url = element?.select("a.button")?.attr("href").toString()

    @Ignore
    fun addElements(elements: Elements): List<NewChapterResponse> {
        val listElements = mutableListOf<NewChapterResponse>()
        elements.mapNotNull {
            listElements.add(NewChapterResponse(it))
        }

        return listElements
    }
}
