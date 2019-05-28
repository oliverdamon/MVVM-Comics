package com.example.mangavinek

import org.jsoup.nodes.Element

data class Model(var element: Element){
    val title = element.select(".thead").text().toString()
    val image = element.select("img").attr("src").toString()
}