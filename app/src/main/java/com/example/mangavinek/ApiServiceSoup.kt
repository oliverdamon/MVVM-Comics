package com.example.mangavinek

import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getList(url: String): Elements {
        return JSoupService.getApiClient(url).select("td table.tborder")
    }
}