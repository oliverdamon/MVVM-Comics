package com.example.mangavinek.data.model.favorite.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite", indices = [Index(value = ["title", "image", "link"], unique = true)])
data class Favorite(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val title: String,
        val image: String,
        val link: String)