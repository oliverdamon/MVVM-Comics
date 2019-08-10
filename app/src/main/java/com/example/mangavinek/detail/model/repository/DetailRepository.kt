package com.example.mangavinek.detail.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.domain.entity.DetailChapterResponse
import com.example.mangavinek.detail.model.domain.entity.DetailResponse
import com.example.mangavinek.detail.model.domain.entity.getItemDetail
import com.example.mangavinek.detail.model.domain.entity.getItems

class DetailRepository {

    fun getDetail(url: String): DetailResponse? = ApiServiceSoup.getDetail(url).getItemDetail()

    fun getDetailChapter(url: String): MutableList<DetailChapterResponse>? {
        val movieResponse = ApiServiceSoup.getDetailChapter(url)
        return movieResponse.getItems()
    }
}