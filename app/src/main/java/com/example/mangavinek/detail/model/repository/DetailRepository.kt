package com.example.mangavinek.detail.model.repository

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.domain.entity.*

class DetailRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getDetail(url: String): DetailResponse? = apiServiceSoup.getDetail(url).getItemDetail()

    fun getDetailChapter(url: String): MutableList<DetailChapterResponse>? {
        val movieResponse = apiServiceSoup.getDetailChapter(url)
        return movieResponse.getItems()
    }

    fun mergeStatusList(detailResponse: DetailResponse): MutableList<StatusChapter> {
        val partsAvailable = detailResponse.issueAvailable.split(" ".toRegex())
        val partsTranslated = detailResponse.issueTranslated.split(" ".toRegex())
        val partsUnavailable = detailResponse.issueUnavailable.split(" ".toRegex())
        val mutableList = mutableListOf<StatusChapter>()

        partsAvailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("available", it)
                mutableList.add(statusChapter)
            }
        }

        partsTranslated.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("translated", it)
                mutableList.add(statusChapter)
            }
        }

        partsUnavailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("unavailable", it)
                mutableList.add(statusChapter)
            }
        }

        mutableList.sortWith(compareBy(StatusChapter::number))

        return mutableList
    }
}