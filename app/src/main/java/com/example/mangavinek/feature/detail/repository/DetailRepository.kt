package com.example.mangavinek.feature.detail.repository

import androidx.lifecycle.LiveData
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.data.source.local.dao.FavoriteDao
import com.example.mangavinek.data.source.remote.api.ApiServiceSoup
import com.example.mangavinek.data.model.detail.domain.DetailChapterDomain
import com.example.mangavinek.data.model.detail.domain.DetailDomain
import com.example.mangavinek.data.model.detail.domain.StatusChapterDomain
import com.example.mangavinek.data.model.detail.mapper.DetailChapterMapper
import com.example.mangavinek.data.model.detail.mapper.DetailMapper
import com.example.mangavinek.feature.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.feature.model.detail.entity.DetailResponse

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

    fun findByTitle(title: String): LiveData<FavoriteDB?> {
        return favoriteDao.findByTitle(title)
    }

    suspend fun insertComic(favoriteDB: FavoriteDB){
        favoriteDao.insertComic(favoriteDB)
    }

    suspend fun removeComic(favoriteDB: FavoriteDB){
        favoriteDao.removeComic(favoriteDB.title)
    }
}