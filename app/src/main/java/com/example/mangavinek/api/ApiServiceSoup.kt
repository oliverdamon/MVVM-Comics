package com.example.mangavinek.api

import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getListHot(url: String): Elements {
        return JSoupService.getApiClient(url).select("td table.tborder")
    }
}