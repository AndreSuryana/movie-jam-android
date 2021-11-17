package com.example.moviejam.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MainRepository
) : ViewModel() {

    private val _listTopMovies = MutableLiveData<Event<Resource<List<Movie>>>>()
    val listTopMovies: LiveData<Event<Resource<List<Movie>>>> = _listTopMovies

    private val _listPopularMovies = MutableLiveData<Event<Resource<List<Movie>>>>()
    val listPopularMovies: LiveData<Event<Resource<List<Movie>>>> = _listPopularMovies

    private val _listPopularTvShows = MutableLiveData<Event<Resource<List<TvShow>>>>()
    val listPopularTvShows: LiveData<Event<Resource<List<TvShow>>>> = _listPopularTvShows


    fun setTopMovies() {
        viewModelScope.launch {
            _listTopMovies.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getTrendingMovies()
            val topMovies: List<Movie>? = response.data?.movies

            if (topMovies?.isNotEmpty() == true)
                _listTopMovies.postValue(Event(Resource(Status.SUCCESS, topMovies, null)))
            else
                _listTopMovies.postValue(
                    Event(
                        Resource(
                            Status.ERROR,
                            null,
                            "Sorry! Top Movies is Empty!"
                        )
                    )
                )
        }
    }

    fun setPopularMovies() {
        viewModelScope.launch {
            _listPopularMovies.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getPopularMovies()
            val popularMovies: List<Movie>? = response.data?.movies

            if (popularMovies?.isNotEmpty() == true)
                _listPopularMovies.postValue(Event(Resource(Status.SUCCESS, popularMovies, null)))
            else
                _listPopularMovies.postValue(
                    Event(
                        Resource(
                            Status.ERROR,
                            null,
                            "Sorry! Popular Movies is Empty!"
                        )
                    )
                )
        }
    }

    fun setPopularTvShows() {
        viewModelScope.launch {
            _listPopularTvShows.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getPopularTvShows()
            val popularTvShows: List<TvShow>? = response.data?.tvShows

            if (popularTvShows?.isNotEmpty() == true)
                _listPopularTvShows.postValue(Event(Resource(Status.SUCCESS, popularTvShows, null)))
            else
                _listPopularTvShows.postValue(
                    Event(
                        Resource(
                            Status.ERROR,
                            null,
                            "Sorry! Popular TV Shows is Empty!"
                        )
                    )
                )
        }
    }
}