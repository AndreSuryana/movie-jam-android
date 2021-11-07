package com.example.moviejam.ui.movies

import android.content.Context
import androidx.lifecycle.*
import com.example.moviejam.data.DataEntity
import com.example.moviejam.repository.MainRepository

class MoviesViewModel constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class MoviesEvent {
        class Success(val movies: List<DataEntity>) : MoviesEvent()
        class Failure(val message: String) : MoviesEvent()
        object Loading : MoviesEvent()
        object Empty : MoviesEvent()
    }

    private val _listMovies = MutableLiveData<MoviesEvent>(MoviesEvent.Empty)
    val listMovies: LiveData<MoviesEvent> = _listMovies

    fun setMovies(context: Context?) {
        _listMovies.value = MoviesEvent.Loading
        val movies = repository.getMovies(context?.applicationContext)
        if (movies.isNotEmpty())
            _listMovies.value = MoviesEvent.Success(movies)
        else
            _listMovies.value = MoviesEvent.Failure("An error has been occurred!")
    }
}