package com.example.mangavinek.presentation.favorite.viewmodel

import androidx.lifecycle.LiveData
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.model.favorite.entity.FavoriteDB
import com.example.mangavinek.data.repository.favorite.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : BaseViewModel() {

    var getLiveDataListFavoriteDB: LiveData<List<FavoriteDB>>? = null

    init {
        getComicList()
    }

    private fun getComicList() {
        getLiveDataListFavoriteDB = repository.getListComic()
    }
}