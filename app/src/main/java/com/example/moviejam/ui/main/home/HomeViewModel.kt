package com.example.moviejam.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.repository.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository?
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

    init {
        setTopMovies()
        setPopularMovies()
        setPopularTvShows()
    }

    private fun setTopMovies() {
        viewModelScope.launch {
            _listTopMovies.value = MoviesEvent.Loading
            val topMovies = repository?.getTopMovies()
            if (topMovies?.isNotEmpty() == true)
                _listTopMovies.value = MoviesEvent.Success(topMovies)
            else
                _listTopMovies.value = MoviesEvent.Failure("An error has been occurred!")
        }
    }

    private fun setPopularMovies() {
        viewModelScope.launch {
            _listPopularMovies.value = MoviesEvent.Loading
            val popularMovies = repository?.getPopularMovies()
            if (popularMovies?.isNotEmpty() == true)
                _listPopularMovies.value = MoviesEvent.Success(popularMovies)
            else
                _listPopularMovies.value = MoviesEvent.Failure("An error has been occurred!")
        }
    }

    private fun setPopularTvShows() {
        viewModelScope.launch {
            _listPopularTvShows.value = TvShowsEvent.Loading
            val popularTvShows = repository?.getPopularTvShows()
            if (popularTvShows?.isNotEmpty() == true)
                _listPopularTvShows.value = TvShowsEvent.Success(popularTvShows)
            else
                _listPopularTvShows.value = TvShowsEvent.Failure("An error has been occurred!")
        }
    }
}