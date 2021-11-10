package com.example.moviejam.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviejam.data.model.DataEntity

class FakeRepository : MainRepository {

    private val topMovies = mutableListOf<DataEntity>()
    private val popularMovies = mutableListOf<DataEntity>()
    private val popularTvShows = mutableListOf<DataEntity>()
    private val movies = mutableListOf<DataEntity>()
    private val tvShows = mutableListOf<DataEntity>()

    private val observableTopMovies = MutableLiveData<List<DataEntity>>(topMovies)
    private val observablePopularMovies = MutableLiveData<List<DataEntity>>(popularMovies)
    private val observablePopularTvShows = MutableLiveData<List<DataEntity>>(popularTvShows)
    private val observableMovies = MutableLiveData<List<DataEntity>>(movies)
    private val observableTvShows = MutableLiveData<List<DataEntity>>(tvShows)

    fun setTopMoviesList(list: List<DataEntity>) {
        topMovies.addAll(list)
        refreshLiveDataList()
    }

    fun setPopularMoviesList(list: List<DataEntity>) {
        popularMovies.addAll(list)
        refreshLiveDataList()
    }

    fun setPopularTvShowsList(list: List<DataEntity>) {
        popularTvShows.addAll(list)
        refreshLiveDataList()
    }

    fun setMoviesList(list: List<DataEntity>) {
        movies.addAll(list)
        refreshLiveDataList()
    }

    fun setTvShowsList(list: List<DataEntity>) {
        tvShows.addAll(list)
        refreshLiveDataList()
    }

    private fun refreshLiveDataList() {
        observableTopMovies.postValue(topMovies)
        observablePopularMovies.postValue(popularMovies)
        observablePopularTvShows.postValue(popularTvShows)
        observableMovies.postValue(movies)
        observableTvShows.postValue(tvShows)
    }

    override suspend fun getTopMovies(): List<DataEntity> = topMovies

    override suspend fun getPopularMovies(): List<DataEntity> = popularMovies

    override suspend fun getPopularTvShows(): List<DataEntity> = popularTvShows

    override suspend fun getMovies(): List<DataEntity> = movies

    override suspend fun getTvShows(): List<DataEntity> = tvShows
}