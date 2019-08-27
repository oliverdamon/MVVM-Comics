package com.example.mangavinek.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mangavinek.data.model.favorite.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(favorite: Favorite): Long

    @Query("SELECT * FROM favorite")
    fun getComic(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE title = :title")
    fun verifyItem(title: String): Int

    @Query("DELETE FROM favorite WHERE title = :title")
    fun removeComic(title: String)
}