package com.example.moviejam.repository

import android.content.Context
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.utils.DummyData

class DummyDataRepository(private val context: Context?) : MainRepository {

    override suspend fun getTopMovies(): List<DataEntity> =
        DummyData.generateDummyTopMovies(context?.applicationContext)

    override suspend fun getPopularMovies(): List<DataEntity> =
        DummyData.generateDummyPopularMovies(context?.applicationContext)

    override suspend fun getPopularTvShows(): List<DataEntity> =
        DummyData.generateDummyPopularTvShows(context?.applicationContext)

    override suspend fun getMovies(): List<DataEntity> =
        DummyData.generateDummyMovies(context?.applicationContext)

    override suspend fun getMovieById(id: Int): DataEntity =
        DummyData.getDummyMovieById(context?.applicationContext, id)

    override suspend fun getTvShows(): List<DataEntity> =
        DummyData.generateDummyTvShows(context?.applicationContext)

    override suspend fun getTvShowById(id: Int): DataEntity =
        DummyData.getDummyTvShowById(context?.applicationContext, id)
}