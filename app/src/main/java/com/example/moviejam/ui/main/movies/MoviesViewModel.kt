package com.example.moviejam.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _listMovies = MutableLiveData<Event<Resource<List<Movie>>>>()
    val listMovies: LiveData<Event<Resource<List<Movie>>>> = _listMovies

    fun setMovies() {
        viewModelScope.launch {
            _listMovies.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getMovies()
            val movies: List<Movie>? = response.data?.movies

            if (movies?.isNotEmpty() == true)
                _listMovies.postValue(Event(Resource(Status.SUCCESS, movies, null)))
            else
                _listMovies.postValue(Event(Resource(Status.ERROR, null, response.message ?: "Movies is Empty!")))
        }
    }
}