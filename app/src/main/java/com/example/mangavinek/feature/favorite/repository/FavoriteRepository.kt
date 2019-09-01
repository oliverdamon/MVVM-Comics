package com.example.mangavinek.feature.favorite.repository

import androidx.lifecycle.LiveData
import com.example.mangavinek.data.model.favorite.entity.Favorite
import com.example.mangavinek.data.source.local.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    fun getListComic(): LiveData<List<Favorite>> {
        return favoriteDao.getComic()
    }
}