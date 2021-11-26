package com.example.moviejam.data.source.local

import androidx.paging.DataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity

interface LocalDataSource {

    fun getListFavoriteMovies(): DataSource.Factory<Int, FavoriteEntity>
    fun getListFavoriteTvShows(): DataSource.Factory<Int, FavoriteEntity>
    suspend fun checkFavoriteMovies(id: Int?): Int?
    suspend fun checkFavoriteTvShows(id: Int?): Int?
    suspend fun insertToFavorites(fav: FavoriteEntity)
    suspend fun deleteFromFavorites(id: Int?)
}