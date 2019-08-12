package com.example.mangavinek.detail.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.domain.entity.DetailChapterResponse
import com.example.mangavinek.detail.model.domain.entity.DetailResponse
import com.example.mangavinek.detail.model.domain.entity.getItemDetail
import com.example.mangavinek.detail.model.domain.entity.getItems

class DetailRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getDetail(url: String): DetailResponse? = apiServiceSoup.getDetail(url).getItemDetail()

    fun getDetailChapter(url: String): MutableList<DetailChapterResponse>? {
        val movieResponse = apiServiceSoup.getDetailChapter(url)
        return movieResponse.getItems()
    }
}