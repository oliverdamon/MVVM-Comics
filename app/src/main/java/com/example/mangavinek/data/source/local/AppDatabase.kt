package com.example.mangavinek.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mangavinek.data.source.local.dao.FavoriteDao
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.feature.model.home.entity.NewChapterResponse

@Database(entities = [FavoriteDB::class, NewChapterResponse::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorite_database")
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}