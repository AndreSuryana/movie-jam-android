package com.example.moviejam.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.repository.MainRepository
import kotlinx.coroutines.launch

class TvShowsViewModel(
    private val repository: MainRepository?
) : ViewModel() {

    sealed class TvShowsEvent {
        class Success(val tvShows: List<DataEntity>) : TvShowsEvent()
        class Failure(val message: String) : TvShowsEvent()
        object Loading : TvShowsEvent()
        object Empty : TvShowsEvent()
    }

    private val _listTvShows = MutableLiveData<TvShowsEvent>(TvShowsEvent.Empty)
    val listTvShows: LiveData<TvShowsEvent> = _listTvShows

    init {
        setTvShows()
    }

    private fun setTvShows() {
        viewModelScope.launch {
            _listTvShows.value = TvShowsEvent.Loading
            val tvShows = repository?.getTvShows()
            if (tvShows?.isNotEmpty() == true)
                _listTvShows.value = TvShowsEvent.Success(tvShows)
            else
                _listTvShows.value = TvShowsEvent.Failure("An error has been occurred!")
        }
    }
}