package com.example.mangavinek.data.repository.home

import com.example.mangavinek.core.base.BaseRepository
import com.example.mangavinek.model.home.entity.NewChapterResponse
import com.example.mangavinek.data.source.local.dao.FavoriteDao
import com.example.mangavinek.data.source.remote.api.ApiServiceSoup
import com.example.mangavinek.model.home.domain.NewChapterDomain
import com.example.mangavinek.model.home.mapper.HomeMapper

class HomeRepository(private val apiServiceSoup: ApiServiceSoup, val lancamentosDao: FavoriteDao): BaseRepository() {

    suspend fun getComics(url: String): List<NewChapterDomain>?{
        return networkBoundResource (
            makeApiCall = { NewChapterResponse().addElements(apiServiceSoup.getListHot(url)) })
            .transform { HomeMapper.transformEntityToDomain(it) }
    }
}

