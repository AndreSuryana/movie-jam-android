package com.example.moviejam.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _listTvShows = MutableLiveData<Event<Resource<List<TvShow>>>>()
    val listTvShows: LiveData<Event<Resource<List<TvShow>>>> = _listTvShows

    fun setTvShows() {
        viewModelScope.launch {
            _listTvShows.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getTvShows()
            val tvShows: List<TvShow>? = response.data?.tvShows

            if (tvShows?.isNotEmpty() == true)
                _listTvShows.postValue(Event(Resource(Status.SUCCESS, tvShows, null)))
            else
                _listTvShows.postValue(Event(Resource(Status.ERROR, null, response.message ?: "TV Shows is Empty!")))
        }
    }
}