package com.example.moviejam.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviejam.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}