package com.example.moviejam.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviejam.data.source.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteentities WHERE isMovie = 1")
    fun getListFavoriteMovies(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteentities WHERE isMovie = 0")
    fun getListFavoriteTvShows(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM favoriteentities WHERE isMovie = 1 AND id = :id")
    suspend fun checkFavoriteMovies(id: Int?): Int?

    @Query("SELECT count(*) FROM favoriteentities WHERE isMovie = 0 AND id = :id")
    suspend fun checkFavoriteTvShows(id: Int?): Int?

    @Insert
    suspend fun insertToFavorites(fav: FavoriteEntity)

    @Query("DELETE FROM favoriteentities WHERE id = :id")
    suspend fun deleteFromFavorites(id: Int?)
}