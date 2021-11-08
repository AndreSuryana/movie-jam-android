package com.example.moviejam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviejam.data.model.DataEntity

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addToFavorite(favData: DataEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getFavoriteList(): List<DataEntity>

    @Query("SELECT count(*) FROM favorite WHERE favorite.title = :title")
    suspend fun checkFavoriteItem(title: String): Int

    @Query("DELETE FROM favorite WHERE favorite.title = :title")
    suspend fun deleteFavoriteItem(title: String): Int
}