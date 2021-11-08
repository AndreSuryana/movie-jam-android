package com.example.moviejam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviejam.data.local.dao.FavoriteDao
import com.example.moviejam.data.model.DataEntity

@Database(
    entities = [DataEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}