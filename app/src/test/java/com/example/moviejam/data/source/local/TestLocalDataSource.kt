package com.example.moviejam.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviejam.data.source.local.entity.FavoriteEntity

class TestLocalDataSource : LocalDataSource {

    private val favoriteMovies = mutableListOf<FavoriteEntity>()
    private val favoriteTvShows = mutableListOf<FavoriteEntity>()

    private val observableFavoriteMovies = MutableLiveData<List<FavoriteEntity>>(favoriteMovies)
    private val observableFavoriteTvShows = MutableLiveData<List<FavoriteEntity>>(favoriteTvShows)

    fun setFavoriteMovies(list: List<FavoriteEntity>) {
        favoriteMovies.addAll(list)
        refreshLiveDataList()
    }

    fun setFavoriteTvShows(list: List<FavoriteEntity>) {
        favoriteTvShows.addAll(list)
        refreshLiveDataList()
    }

    private fun refreshLiveDataList() {
        observableFavoriteMovies.postValue(favoriteMovies)
        observableFavoriteTvShows.postValue(favoriteTvShows)
    }

    override fun getListFavoriteMovies(): LiveData<List<FavoriteEntity>> {

    }

    override fun getListFavoriteTvShows(): LiveData<List<FavoriteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkFavoriteMovies(id: Int?): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun checkFavoriteTvShows(id: Int?): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun insertToFavorites(fav: FavoriteEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(id: Int?) {
        TODO("Not yet implemented")
    }
}