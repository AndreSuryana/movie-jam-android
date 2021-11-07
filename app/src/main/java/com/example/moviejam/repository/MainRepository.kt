package com.example.moviejam.repository

import android.content.Context
import com.example.moviejam.data.DataEntity

interface MainRepository {

    fun getTopMovies(context: Context?): List<DataEntity>
    fun getPopularMovies(context: Context?): List<DataEntity>
    fun getPopularTvShows(context: Context?): List<DataEntity>
    fun getMovies(context: Context?): List<DataEntity>
    fun getMovieById(context: Context?, id: Int): DataEntity
    fun getTvShows(context: Context?): List<DataEntity>
    fun getTvShowById(context: Context?, id: Int): DataEntity
}