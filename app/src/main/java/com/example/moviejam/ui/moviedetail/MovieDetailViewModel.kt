package com.example.moviejam.ui.moviedetail

import androidx.lifecycle.*
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun getData(id: Int): LiveData<Resource<MovieDetailResponse>> = runBlocking {
        repository.getMovieDetail(id.toString())
    }

    suspend fun addMovieToFavorites(movie: MovieDetailResponse, isMovie: Boolean = true) {
        viewModelScope.launch {
            val favMovie = FavoriteEntity(
                movie.id,
                movie.posterPath,
                movie.title,
                movie.voteAverage,
                movie.releaseDate,
                isMovie
            )
            repository.insertToFavorites(favMovie)
        }
    }

    suspend fun checkFavoriteMovie(id: Int?): Int? =
        repository.checkFavoriteMovies(id)

    fun deleteMovieFromFavorites(id: Int?) {
        viewModelScope.launch {
            repository.deleteFromFavorites(id)
        }
    }
}