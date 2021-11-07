package com.example.moviejam.repository

import android.content.Context
import com.example.moviejam.data.DataEntity
import com.example.moviejam.utils.DummyData

class DummyDataRepository : MainRepository {

    override fun getTopMovies(context: Context?): List<DataEntity> =
        DummyData.generateDummyTopMovies(context?.applicationContext)

    override fun getPopularMovies(context: Context?): List<DataEntity> =
        DummyData.generateDummyPopularMovies(context?.applicationContext)

    override fun getPopularTvShows(context: Context?): List<DataEntity> =
        DummyData.generateDummyPopularTvShows(context?.applicationContext)

    override fun getMovies(context: Context?): List<DataEntity> =
        DummyData.generateDummyMovies(context?.applicationContext)

    override fun getMovieById(context: Context?, id: Int): DataEntity =
        DummyData.getDummyMovieById(context?.applicationContext, id)

    override fun getTvShows(context: Context?): List<DataEntity> =
        DummyData.generateDummyTvShows(context?.applicationContext)

    override fun getTvShowById(context: Context?, id: Int): DataEntity =
        DummyData.getDummyTvShowById(context?.applicationContext, id)
}