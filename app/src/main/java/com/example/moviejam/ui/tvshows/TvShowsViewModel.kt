package com.example.moviejam.ui.tvshows

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.DataEntity
import com.example.moviejam.repository.MainRepository

class TvShowsViewModel constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class TvShowsEvent {
        class Success(val tvShows: List<DataEntity>) : TvShowsEvent()
        class Failure(val message: String) : TvShowsEvent()
        object Loading : TvShowsEvent()
        object Empty : TvShowsEvent()
    }

    private val _listTvShows = MutableLiveData<TvShowsEvent>(TvShowsEvent.Empty)
    val listTvShows: LiveData<TvShowsEvent> = _listTvShows

    fun setTvShows(context: Context?) {
        _listTvShows.value = TvShowsEvent.Loading
        val tvShows = repository.getTvShows(context?.applicationContext)
        if (tvShows.isNotEmpty())
            _listTvShows.value = TvShowsEvent.Success(tvShows)
        else
            _listTvShows.value = TvShowsEvent.Failure("An error has been occurred!")
    }
}