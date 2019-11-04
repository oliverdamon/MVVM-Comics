package com.example.mangavinek.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mangavinek.data.entity.favorite.FavoriteDB

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(favoriteDB: FavoriteDB): Long

    @Query("SELECT * FROM favorite")
    fun getComic(): LiveData<List<FavoriteDB>>

    @Query("SELECT * FROM favorite WHERE title = :title")
    fun searchForTitle(title: String): Int

    @Query("DELETE FROM favorite WHERE title = :title")
    fun removeComic(title: String)
}