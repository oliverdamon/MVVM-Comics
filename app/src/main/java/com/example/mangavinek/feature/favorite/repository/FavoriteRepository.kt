package com.example.mangavinek.feature.favorite.repository

import androidx.lifecycle.LiveData
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.data.source.local.dao.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    fun getListComic(): LiveData<List<FavoriteDB>> {
        return favoriteDao.getComic()
    }
}