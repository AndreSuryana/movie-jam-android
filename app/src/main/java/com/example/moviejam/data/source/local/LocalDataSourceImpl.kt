package com.example.moviejam.data.source.local

import androidx.paging.DataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.local.room.FavoriteDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : LocalDataSource {

    override fun getListFavoriteMovies(): DataSource.Factory<Int, FavoriteEntity> =
        favoriteDao.getListFavoriteMovies()

    override fun getListFavoriteTvShows(): DataSource.Factory<Int, FavoriteEntity> =
        favoriteDao.getListFavoriteTvShows()

    override suspend fun checkFavoriteMovies(id: Int?): Int? =
        favoriteDao.checkFavoriteMovies(id)

    override suspend fun checkFavoriteTvShows(id: Int?): Int? =
        favoriteDao.checkFavoriteTvShows(id)

    override suspend fun insertToFavorites(fav: FavoriteEntity) =
        favoriteDao.insertToFavorites(fav)

    override suspend fun deleteFromFavorites(id: Int?) =
        favoriteDao.deleteFromFavorites(id)
}