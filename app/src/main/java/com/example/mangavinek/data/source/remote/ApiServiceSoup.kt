package com.example.mangavinek.data.source.remote

import com.example.mangavinek.core.helper.loggingInterceptorJSoupElement
import com.example.mangavinek.core.service.JSoupService
import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getListHot(url: String): Elements {
        return JSoupService.getApiClient(url).select("td table.tborder")
            .loggingInterceptorJSoupElement()
    }

    fun getDetail(url: String): Elements {
        return JSoupService.getApiClient(url).select(".forums").loggingInterceptorJSoupElement()
    }

    fun getDetailChapter(url: String): Elements {
        return JSoupService.getApiClient(url).select(".capa a").loggingInterceptorJSoupElement()
    }

    fun getListCatalog(url: String): Elements {
        return JSoupService.getApiClient(url).select("div div div table tbody tr td table.tborder")
            .loggingInterceptorJSoupElement()
    }

    fun getCatalogLastPagination(url: String): Elements {
        return JSoupService.getApiClient(url).select("a.pagination_last")
            .loggingInterceptorJSoupElement()
    }
}