package com.example.moviejam.data.source.local

import androidx.lifecycle.LiveData
import com.example.moviejam.data.source.local.entity.FavoriteEntity

interface LocalDataSource {

    fun getListFavoriteMovies(): LiveData<List<FavoriteEntity>>

    fun getListFavoriteTvShows(): LiveData<List<FavoriteEntity>>

    suspend fun checkFavoriteMovies(id: Int?): Int?

    suspend fun checkFavoriteTvShows(id: Int?): Int?

    suspend fun insertToFavorites(fav: FavoriteEntity)

    suspend fun deleteFromFavorites(id: Int?)
}