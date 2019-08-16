package com.example.mangavinek.core.api

import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getListHot(url: String): Elements {
        return JSoupService.getApiClient(url).select("td table.tborder")
    }

    fun getDetail(url: String): Elements {
        return JSoupService.getApiClient(url).select(".forums")
    }

    fun getDetailChapter(url: String): Elements {
        return JSoupService.getApiClient(url).select(".capa a")
    }

    fun getListCatalog(url: String): Elements {
        return JSoupService.getApiClient(url).select("div div div table tbody tr td table.tborder")
    }

    fun getCatalogLastPagination(url: String): Elements {
        return JSoupService.getApiClient(url).select("a.pagination_last")
    }
}