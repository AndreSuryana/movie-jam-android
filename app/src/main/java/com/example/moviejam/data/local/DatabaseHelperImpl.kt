package com.example.moviejam.data.local

import com.example.moviejam.data.model.DataEntity

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun addToFavorite(favData: DataEntity) =
        appDatabase.favoriteDao().addToFavorite(favData)

    override suspend fun getFavoriteList(): List<DataEntity> =
        appDatabase.favoriteDao().getFavoriteList()

    override suspend fun checkFavoriteItem(title: String): Int =
        appDatabase.favoriteDao().checkFavoriteItem(title)

    override suspend fun deleteFavoriteItem(title: String): Int =
        appDatabase.favoriteDao().deleteFavoriteItem(title)
}