package com.example.moviejam.ui.main.movies


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MainRepository?
) : ViewModel() {

    private val _listMovies = MutableLiveData<Event<Resource<List<DataEntity>>>>()
    val listMovies: LiveData<Event<Resource<List<DataEntity>>>> = _listMovies

    init {
        viewModelScope.launch {
            val movies = repository?.getMovies()
            setMovies(movies)
        }
    }

    fun setMovies(movies: List<DataEntity>?) {
        _listMovies.value = Event(Resource(Status.LOADING, null, null))
        if (movies?.isNotEmpty() == true)
            _listMovies.value = Event(Resource(Status.SUCCESS, movies, null))
        else
            _listMovies.value = Event(Resource(Status.ERROR, null, "Movies List is Empty!"))
    }
}