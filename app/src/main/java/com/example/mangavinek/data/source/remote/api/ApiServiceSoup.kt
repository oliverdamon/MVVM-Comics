package com.example.mangavinek.data.source.remote.api

import com.example.mangavinek.core.helper.loggingInterceptorJSoupElement
import com.example.mangavinek.data.source.remote.ApiClientSoup

import org.jsoup.select.Elements

object ApiServiceSoup {
    fun getListHot(url: String): Elements {
        return ApiClientSoup.requestSoup(url).select("td table.tborder").loggingInterceptorJSoupElement()
    }

    fun getDetail(url: String): Elements {
        return ApiClientSoup.requestSoup(url).select(".forums").loggingInterceptorJSoupElement()
    }

    fun getDetailChapter(url: String): Elements {
        return ApiClientSoup.requestSoup(url).select(".capa a").loggingInterceptorJSoupElement()
    }

    fun getListCatalog(url: String): Elements {
        return ApiClientSoup.requestSoup(url).select("div div div table tbody tr td table.tborder")
            .loggingInterceptorJSoupElement()
    }

    fun getLastPagination(url: String): Elements {
        return ApiClientSoup.requestSoup(url).select(".pagination a").loggingInterceptorJSoupElement()
    }
}