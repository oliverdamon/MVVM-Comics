package com.example.mangavinek.feature.home.repository

import com.example.mangavinek.core.base.BaseRepository
import com.example.mangavinek.data.entity.home.NewChapterResponse
import com.example.mangavinek.data.source.local.FavoriteDao
import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import com.example.mangavinek.feature.home.model.mapper.HomeMapper

class HomeRepository(private val apiServiceSoup: ApiServiceSoup, val lancamentosDao: FavoriteDao): BaseRepository() {

    suspend fun getComics(url: String): List<NewChapterDomain>?{
        return networkBoundResource (
            makeApiCall = { NewChapterResponse().addElements(apiServiceSoup.getListHot(url)) })
            .transform { HomeMapper.transformEntityToDomain(it) }
    }
}

