package com.example.mangavinek.feature.detail.repository

import com.example.mangavinek.data.entity.detail.*
import com.example.mangavinek.data.entity.favorite.FavoriteDB
import com.example.mangavinek.data.source.local.FavoriteDao
import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.feature.detail.model.domain.DetailChapterDomain
import com.example.mangavinek.feature.detail.model.domain.DetailDomain
import com.example.mangavinek.feature.detail.model.domain.StatusChapterDomain
import com.example.mangavinek.feature.detail.model.mapper.DetailChapterMapper
import com.example.mangavinek.feature.detail.model.mapper.DetailMapper

class DetailRepository(private val apiServiceSoup: ApiServiceSoup, private val favoriteDao: FavoriteDao) {

    private lateinit var detailResponse: DetailResponse

    fun getDetail(url: String): DetailDomain?{
        detailResponse = DetailResponse().addElements(apiServiceSoup.getDetail(url))
        return DetailMapper.transformEntityToDomain(detailResponse)
    }

    fun getListDetailChapter(url: String): List<DetailChapterDomain>? {
        val detailChapterResponse = DetailChapterResponse()
            .addElements(apiServiceSoup.getDetailChapter(url))

        return DetailChapterMapper.transformEntityToDomain(detailChapterResponse)
    }

    fun getListDetailStatusChapter(): MutableList<StatusChapterDomain> {
        return DetailMapper.transformCommonStatusToCustomStatus(detailResponse)
    }

    fun searchForTitle(title: String): Int {
        return favoriteDao.searchForTitle(title)
    }

    fun insertComic(favoriteDB: FavoriteDB){
        favoriteDao.insertComic(favoriteDB)
    }

    fun removeComic(title: String){
        favoriteDao.removeComic(title)
    }
}