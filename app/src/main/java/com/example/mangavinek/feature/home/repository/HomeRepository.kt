package com.example.mangavinek.feature.home.repository

import com.example.mangavinek.data.entity.home.NewChapterResponse
import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import com.example.mangavinek.feature.home.model.mapper.HomeMapper

class HomeRepository(private val apiServiceSoup: ApiServiceSoup) {

    fun getListNewChapterDomain(url: String): List<NewChapterDomain> {
        val listHomeResponse = NewChapterResponse().addElements(apiServiceSoup.getListHot(url))
        return HomeMapper.transformEntityToDomain(listHomeResponse)
    }
}

