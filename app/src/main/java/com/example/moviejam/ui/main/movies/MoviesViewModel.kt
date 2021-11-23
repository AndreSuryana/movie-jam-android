package com.example.moviejam.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun searchMovies(query: String?): LiveData<PagingData<Movie>> =
        if (query != null)
            repository.searchMovies(query)
        else
            repository.getMovies()

    suspend fun setMovies(): LiveData<PagingData<Movie>> =
        repository.getMovies()
}