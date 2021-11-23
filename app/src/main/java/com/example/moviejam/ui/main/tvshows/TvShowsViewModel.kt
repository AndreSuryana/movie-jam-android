package com.example.moviejam.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun searchTvShows(query: String?): LiveData<PagingData<TvShow>> =
        if (query != null)
            repository.searchTvShows(query)
        else
            repository.getTvShows()

    suspend fun setTvShows(): LiveData<PagingData<TvShow>> =
        repository.getTvShows()
}