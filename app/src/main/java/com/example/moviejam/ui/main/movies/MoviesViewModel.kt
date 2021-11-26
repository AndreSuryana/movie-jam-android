package com.example.moviejam.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val STARTING_PAGE = 1

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun searchMovies(query: String?): LiveData<Resource<MoviesResponse>> =
        if (query != null)
            repository.searchMovies(query, STARTING_PAGE)
        else
            repository.getMovies(STARTING_PAGE)

    suspend fun getMovies(): LiveData<Resource<MoviesResponse>> =
        repository.getMovies(STARTING_PAGE)
}