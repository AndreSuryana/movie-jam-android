package com.example.moviejam.ui.main.tvshows

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

class TvShowsViewModel(
    private val repository: MainRepository?
) : ViewModel() {

    private val _listTvShows = MutableLiveData<Event<Resource<List<DataEntity>>>>()
    val listTvShows: LiveData<Event<Resource<List<DataEntity>>>> = _listTvShows

    fun setTvShows() {
        viewModelScope.launch {
            val tvShows: List<DataEntity>? = repository?.getTvShows()

            _listTvShows.value = Event(Resource(Status.LOADING, null, null))
            if (tvShows?.isNotEmpty() == true)
                _listTvShows.value = Event(Resource(Status.SUCCESS, tvShows, null))
            else
                _listTvShows.value = Event(Resource(Status.ERROR, null, "TV Shows is Empty!"))
        }
    }
}