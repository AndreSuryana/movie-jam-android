package com.example.moviejam.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.DataEntity
import com.example.moviejam.repository.MainRepository

class HomeViewModel constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class MoviesEvent {
        class Success(val movies: List<DataEntity>) : MoviesEvent()
        class Failure(val message: String) : MoviesEvent()
        object Loading : MoviesEvent()
        object Empty : MoviesEvent()
    }

    sealed class TvShowsEvent {
        class Success(val tvShows: List<DataEntity>) : TvShowsEvent()
        class Failure(val message: String) : TvShowsEvent()
        object Loading : TvShowsEvent()
        object Empty : TvShowsEvent()
    }

    private val _listTopMovies = MutableLiveData<MoviesEvent>(MoviesEvent.Empty)
    val listTopMovies: LiveData<MoviesEvent> = _listTopMovies

    private val _listPopularMovies = MutableLiveData<MoviesEvent>(MoviesEvent.Empty)
    val listPopularMovies: LiveData<MoviesEvent> = _listPopularMovies

    private val _listPopularTvShows = MutableLiveData<TvShowsEvent>(TvShowsEvent.Empty)
    val listPopularTvShows: LiveData<TvShowsEvent> = _listPopularTvShows

    fun setTopMovies(context: Context?) {
        _listTopMovies.value = MoviesEvent.Loading
        val topMovies = repository.getTopMovies(context?.applicationContext)
        if (topMovies.isNotEmpty())
            _listTopMovies.value = MoviesEvent.Success(topMovies)
        else
            _listTopMovies.value = MoviesEvent.Failure("An error has been occurred!")
    }

    fun setPopularMovies(context: Context?) {
        _listPopularMovies.value = MoviesEvent.Loading
        val popularMovies = repository.getPopularMovies(context?.applicationContext)
        if (popularMovies.isNotEmpty())
            _listPopularMovies.value = MoviesEvent.Success(popularMovies)
        else
            _listPopularMovies.value = MoviesEvent.Failure("An error has been occurred!")
    }

    fun setPopularTvShows(context: Context?) {
        _listPopularTvShows.value = TvShowsEvent.Loading
        val popularTvShows = repository.getPopularTvShows(context?.applicationContext)
        if (popularTvShows.isNotEmpty())
            _listPopularTvShows.value = TvShowsEvent.Success(popularTvShows)
        else
            _listPopularTvShows.value = TvShowsEvent.Failure("An error has been occurred!")
    }
}