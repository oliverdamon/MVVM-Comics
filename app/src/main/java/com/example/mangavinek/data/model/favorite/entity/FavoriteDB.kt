package com.example.mangavinek.feature.model.favorite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteDB(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var title: String = "",
        var image: String = "",
        var link: String = "")