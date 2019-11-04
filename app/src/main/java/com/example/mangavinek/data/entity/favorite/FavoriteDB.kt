package com.example.mangavinek.data.entity.favorite

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite", indices = [Index(value = ["title", "image", "link"], unique = true)])
data class FavoriteDB(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val title: String,
        val image: String,
        val link: String)