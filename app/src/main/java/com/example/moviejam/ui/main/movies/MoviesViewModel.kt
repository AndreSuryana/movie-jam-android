package com.example.moviejam.ui.main.movies


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.repository.MainRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MainRepository?
) : ViewModel() {

    sealed class MoviesEvent {
        class Success(val movies: List<DataEntity>) : MoviesEvent()
        class Failure(val message: String) : MoviesEvent()
        object Loading : MoviesEvent()
        object Empty : MoviesEvent()
    }

    private val _listMovies = MutableLiveData<MoviesEvent>(MoviesEvent.Empty)
    val listMovies: LiveData<MoviesEvent> = _listMovies

    init {
        setMovies()
    }

    private fun setMovies() {
        viewModelScope.launch {
            _listMovies.value = MoviesEvent.Loading
            val movies = repository?.getMovies()
            if (movies?.isNotEmpty() == true)
                _listMovies.value = MoviesEvent.Success(movies)
            else
                _listMovies.value = MoviesEvent.Failure("An error has been occurred!")
        }
    }
}