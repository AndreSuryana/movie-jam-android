package com.example.moviejam.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val STARTING_PAGE = 1

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    suspend fun searchTvShows(query: String?): LiveData<Resource<TvShowsResponse>> =
        if (query != null)
            repository.searchTvShows(query, STARTING_PAGE)
        else
            repository.getTvShows(STARTING_PAGE)

    suspend fun getTvShows(): LiveData<Resource<TvShowsResponse>> =
        repository.getTvShows(STARTING_PAGE)
}