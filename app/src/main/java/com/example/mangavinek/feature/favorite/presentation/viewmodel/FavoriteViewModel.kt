package com.example.mangavinek.feature.favorite.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.feature.favorite.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : BaseViewModel() {

    var getLiveDataListFavoriteDB: LiveData<List<FavoriteDB>>? = null

    init {
        getComicList()
    }

    private fun getComicList() {
        getLiveDataListFavoriteDB = repository.getListComic()
    }
}