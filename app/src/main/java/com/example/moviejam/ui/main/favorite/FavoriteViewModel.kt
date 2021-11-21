package com.example.moviejam.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<FavoriteEntity>> =
        localDataSource.getListFavoriteMovies()

    fun getFavoriteTvShows(): LiveData<List<FavoriteEntity>> =
        localDataSource.getListFavoriteTvShows()
}