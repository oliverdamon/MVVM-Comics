package com.example.mangavinek.feature.favorite.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.mangavinek.core.base.BaseViewModel
import com.example.mangavinek.data.model.favorite.entity.Favorite
import com.example.mangavinek.feature.favorite.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : BaseViewModel() {

    var getList: LiveData<List<Favorite>>? = null

    fun getComicList() {
        getList = repository.getListComic()
    }
}