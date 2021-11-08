package com.example.moviejam.utils

import android.content.Context
import android.util.Log
import com.example.moviejam.data.model.DataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DummyData {

    fun generateDummyTopMovies(context: Context?): List<DataEntity> {

        val movies = ArrayList<DataEntity>()

        // Getting data from json with Gson
        val jsonFileString = getJsonDataFromAsset(context?.applicationContext, "top.json")
        Log.i("Top Movies Data: ", jsonFileString.toString())

        val listMoviesType = object : TypeToken<List<DataEntity>>() {}.type
        val listMovies: List<DataEntity> = Gson().fromJson(jsonFileString, listMoviesType)
        listMovies.forEach { movie ->
            movies.add(movie)
        }
        return movies
    }

    fun generateDummyMovies(context: Context?): List<DataEntity> {

        val movies = ArrayList<DataEntity>()

        // Getting data
        val jsonFileString = getJsonDataFromAsset(context?.applicationContext, "movies.json")
        Log.i("Movies Data: ", jsonFileString.toString())

        val listMoviesType = object : TypeToken<List<DataEntity>>() {}.type
        val listMovies: List<DataEntity> = Gson().fromJson(jsonFileString, listMoviesType)
        listMovies.forEach { movie ->
            movies.add(movie)
        }
        return movies
    }

    fun generateDummyTvShows(context: Context?): List<DataEntity> {

        val tvShows = ArrayList<DataEntity>()

        // Getting data
        val jsonFileString = getJsonDataFromAsset(context?.applicationContext, "tv_shows.json")
        Log.i("TV Shows Data: ", jsonFileString.toString())

        val listTvShowsType = object : TypeToken<List<DataEntity>>() {}.type
        val listTvShows: List<DataEntity> = Gson().fromJson(jsonFileString, listTvShowsType)
        listTvShows.forEach { tvShow ->
            tvShows.add(tvShow)
        }
        return tvShows
    }

    fun generateDummyPopularMovies(context: Context?): List<DataEntity> {

        val popularMovies = ArrayList<DataEntity>()
        val limit = 5

        // Getting data
        val listMovies: List<DataEntity> = generateDummyMovies(context?.applicationContext)
        listMovies.forEachIndexed { idx, movie ->
            if (idx < limit) popularMovies.add(movie)
        }
        return popularMovies
    }

    fun generateDummyPopularTvShows(context: Context?): List<DataEntity> {

        val popularTvShows = ArrayList<DataEntity>()
        val limit = 5

        // Getting data
        val listMovies: List<DataEntity> = generateDummyTvShows(context?.applicationContext)
        listMovies.forEachIndexed { idx, tvShow ->
            if (idx < limit) popularTvShows.add(tvShow)
        }
        return popularTvShows
    }

    fun getDummyMovieById(context: Context?, id: Int): DataEntity {

        val listMovies: List<DataEntity> = generateDummyMovies(context?.applicationContext)
        lateinit var movie: DataEntity

        listMovies.forEach {
            if (it.id == id) {
                movie = it
                return@forEach
            }
        }
        return movie
    }

    fun getDummyTvShowById(context: Context?, id: Int): DataEntity {

        val listTvShows: List<DataEntity> = generateDummyTvShows(context?.applicationContext)
        lateinit var tvShow: DataEntity

        listTvShows.forEach {
            if (it.id == id) {
                tvShow = it
                return@forEach
            }
        }
        return tvShow
    }
}