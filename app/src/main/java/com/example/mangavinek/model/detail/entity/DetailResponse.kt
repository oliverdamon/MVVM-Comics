package com.example.mangavinek.model.detail.entity

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class DetailResponse(var element: Element) {
    val title = element.select(".thead strong").text().toString()
    val titleOriginal = element.select("table.smalltext tbody tr:nth-child(2) td:nth-child(3)").text().toString()
    val year = element.select("table.smalltext tbody tr:nth-child(3) td:nth-child(3)").text().toString()
    val publishing = element.select("table.smalltext tbody tr:nth-child(4) td:nth-child(3)").text().toString()
    val publication = element.select("table.smalltext tbody tr:nth-child(5) td:nth-child(3)").text().toString()
    val status = element.select("table.smalltext tbody tr:nth-child(6) td:nth-child(3)").text().toString()
    val chapter = element.select("table.smalltext tbody tr:nth-child(7) td:nth-child(3)").text().toString()
    val sinopse = element.select("table.tborder:nth-child(1) span").text().toString()
    val image = element.select("a.cbimg img").attr("src").toString()
    val issueAvailable = element.select("div.issue-br").text().toString()
    val issueTranslated = element.select("div.issue-tr").text().toString()
    val issueUnavailable = element.select("div.issue-en").text().toString()
    val link = element.select("td.trow1 a.iframe").attr("href").toString()
}

data class DetailChapterResponse(var element: Element) {
    val imageChapter = element.select("img").attr("src").toString()
    val linkChapter = element.select("a").attr("href").toString()
}

data class StatusChapter(val status: String, val number: String)

fun Elements.getItems(): MutableList<DetailChapterResponse> {
    val transformedElements = mutableListOf<DetailChapterResponse>()
    this.mapNotNull { transformedElements.add(DetailChapterResponse(it)) }

    return transformedElements
}