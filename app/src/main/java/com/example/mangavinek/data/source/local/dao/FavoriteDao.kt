package com.example.mangavinek.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(favoriteDB: FavoriteDB)

    @Query("SELECT * FROM favorite")
    fun getComic(): LiveData<List<FavoriteDB>>

    @Query("SELECT * FROM favorite WHERE title = :title")
    fun findByTitle(title: String): LiveData<FavoriteDB?>

    @Query("DELETE FROM favorite WHERE title = :title")
    suspend fun removeComic(title: String)
}