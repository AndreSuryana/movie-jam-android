package com.example.moviejam.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MainRepository
) : ViewModel() {

    fun getTopMovies(): LiveData<Resource<MoviesResponse>> = runBlocking {
        repository.getTrendingMovies()
    }

    fun getPopularMovies(): LiveData<Resource<MoviesResponse>> = runBlocking {
        repository.getPopularMovies()
    }

    fun getPopularTvShows(): LiveData<Resource<TvShowsResponse>> = runBlocking {
        repository.getPopularTvShows()
    }
}