package com.example.moviejam.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getFavoriteMovies(): LiveData<Resource<PagedList<FavoriteEntity>>> =
        repository.getListFavoriteMovies()

    fun getFavoriteTvShows(): LiveData<Resource<PagedList<FavoriteEntity>>> =
        repository.getListFavoriteTvShows()

    fun deleteFromFavorites(id: Int?) {
        viewModelScope.launch {
            repository.deleteFromFavorites(id)
        }
    }
}