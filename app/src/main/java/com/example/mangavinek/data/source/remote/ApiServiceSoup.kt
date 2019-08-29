package com.example.mangavinek.data.source.remote

import com.example.mangavinek.core.helper.loggingJSoupElement
import com.example.mangavinek.core.service.JSoupService
import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getListHot(url: String): Elements {
        return JSoupService.getApiClient(url).select("td table.tborder").loggingJSoupElement()
    }

    fun getDetail(url: String): Elements {
        return JSoupService.getApiClient(url).select(".forums").loggingJSoupElement()
    }

    fun getDetailChapter(url: String): Elements {
        return JSoupService.getApiClient(url).select(".capa a").loggingJSoupElement()
    }

    fun getListCatalog(url: String): Elements {
        return JSoupService.getApiClient(url).select("div div div table tbody tr td table.tborder").loggingJSoupElement()
    }

    fun getCatalogLastPagination(url: String): Elements {
        return JSoupService.getApiClient(url).select("a.pagination_last").loggingJSoupElement()
    }
}