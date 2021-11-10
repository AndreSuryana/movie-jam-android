package com.example.moviejam.ui.main.home

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

class HomeViewModel(
    private val repository: MainRepository?
) : ViewModel() {

    private val _listTopMovies = MutableLiveData<Event<Resource<List<DataEntity>>>>()
    val listTopMovies: LiveData<Event<Resource<List<DataEntity>>>> = _listTopMovies

    private val _listPopularMovies = MutableLiveData<Event<Resource<List<DataEntity>>>>()
    val listPopularMovies: LiveData<Event<Resource<List<DataEntity>>>> = _listPopularMovies

    private val _listPopularTvShows = MutableLiveData<Event<Resource<List<DataEntity>>>>()
    val listPopularTvShows: LiveData<Event<Resource<List<DataEntity>>>> = _listPopularTvShows

    init {
        viewModelScope.launch {
            val topMovies = repository?.getTopMovies()
            val popularMovies = repository?.getPopularMovies()
            val popularTvShows = repository?.getPopularTvShows()
            setTopMovies(topMovies)
            setPopularMovies(popularMovies)
            setPopularTvShows(popularTvShows)
        }
    }

    fun setTopMovies(topMovies: List<DataEntity>?) {
        _listTopMovies.value = Event(Resource(Status.LOADING, null, null))
        if (topMovies?.isNotEmpty() == true)
            _listTopMovies.value = Event(Resource(Status.SUCCESS, topMovies, null))
        else
            _listTopMovies.value = Event(Resource(Status.ERROR, null, "Top Movie List is Empty!"))

    }

    fun setPopularMovies(popularMovies: List<DataEntity>?) {
        _listPopularMovies.value = Event(Resource(Status.LOADING, null, null))
        if (popularMovies?.isNotEmpty() == true)
            _listPopularMovies.value = Event(Resource(Status.SUCCESS, popularMovies, null))
        else
            _listPopularMovies.value = Event(Resource(Status.ERROR, null, "Popular Movie List is Empty!"))

    }

    fun setPopularTvShows(popularTvShows: List<DataEntity>?) {
        _listPopularTvShows.value = Event(Resource(Status.LOADING, null, null))
        if (popularTvShows?.isNotEmpty() == true)
            _listPopularTvShows.value = Event(Resource(Status.SUCCESS, popularTvShows, null))
        else
            _listPopularTvShows.value = Event(Resource(Status.ERROR, null, "Popular TV Show List is Empty!"))

    }

}