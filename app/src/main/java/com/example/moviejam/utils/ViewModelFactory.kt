package com.example.moviejam.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviejam.data.local.DatabaseHelper
import com.example.moviejam.dispatchers.DispatcherProvider
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.ui.detail.DetailViewModel
import com.example.moviejam.ui.main.favorite.FavoriteViewModel
import com.example.moviejam.ui.main.home.HomeViewModel
import com.example.moviejam.ui.main.movies.MoviesViewModel
import com.example.moviejam.ui.main.tvshows.TvShowsViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MainRepository? = null,
    private val database: DatabaseHelper? = null,
    private val dispatcher: DispatcherProvider? = null
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
            return TvShowsViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(database) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown class name ${modelClass.simpleName}")
    }
}