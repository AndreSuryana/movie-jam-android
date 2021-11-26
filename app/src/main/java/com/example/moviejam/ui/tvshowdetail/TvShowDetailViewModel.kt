package com.example.moviejam.ui.tvshowdetail

import androidx.lifecycle.*
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getData(id: Int): LiveData<Resource<TvShowDetailResponse>> = runBlocking {
        repository.getTvShowDetail(id.toString())
    }

    suspend fun addTvShowToFavorite(tvShow: TvShowDetailResponse, isMovie: Boolean = false) {
        viewModelScope.launch {
            val favTvShow = FavoriteEntity(
                tvShow.id,
                tvShow.posterPath,
                tvShow.name,
                tvShow.voteAverage,
                tvShow.firstAirDate,
                isMovie
            )
            repository.insertToFavorites(favTvShow)
        }
    }

    suspend fun checkFavoriteTvShow(id: Int?): Int? =
        repository.checkFavoriteTvShows(id)

    fun deleteTvShowFromFavorite(id: Int?) {
        viewModelScope.launch {
            repository.deleteFromFavorites(id)
        }
    }
}