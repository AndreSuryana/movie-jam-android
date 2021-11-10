package com.example.moviejam.repository

import com.example.moviejam.data.model.DataEntity

interface MainRepository {

    suspend fun getTopMovies(): List<DataEntity>
    suspend fun getPopularMovies(): List<DataEntity>
    suspend fun getPopularTvShows(): List<DataEntity>
    suspend fun getMovies(): List<DataEntity>
    suspend fun getTvShows(): List<DataEntity>
}