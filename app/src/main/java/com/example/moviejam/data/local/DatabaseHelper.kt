package com.example.moviejam.data.local

import com.example.moviejam.data.model.DataEntity

interface DatabaseHelper {

    suspend fun addToFavorite(favData: DataEntity)
    suspend fun getFavoriteList(): List<DataEntity>
    suspend fun checkFavoriteItem(title: String): Int
    suspend fun deleteFavoriteItem(title: String): Int
}