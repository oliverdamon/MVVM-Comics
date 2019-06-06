package com.example.mangavinek.model.detail.entity

import org.jsoup.nodes.Element

data class DetailResponse(var element: Element){
    val title = element.select(".thead strong").text().toString()
    val titleOriginal = element.select("table.smalltext tbody tr:nth-child(2) td:nth-child(3)").text().toString()
    val year = element.select("table.smalltext tbody tr:nth-child(3) td:nth-child(3)").text().toString()
    val publishing = element.select("table.smalltext tbody tr:nth-child(4) td:nth-child(3)").text().toString()
    val publication = element.select("table.smalltext tbody tr:nth-child(5) td:nth-child(3)").text().toString()
    val status = element.select("table.smalltext tbody tr:nth-child(6) td:nth-child(3)").text().toString()
    val chapter = element.select("table.smalltext tbody tr:nth-child(7) td:nth-child(3)").text().toString()
    val sinopse = element.select("table.tborder:nth-child(1) span").text().toString()
    val image = element.select("a.cbimg img").attr("src").toString()
}