package com.example.moviejam.utils

import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.tvshow.TvShow

object DummyData {

    val dummyMovies = listOf(
        Movie(
            1,
            "Data 1",
            "Data 1",
            1.0,
            "Data 1",
            listOf()
        ),
        Movie(
            2,
            "Data 2",
            "Data 2",
            2.0,
            "Data 2",
            listOf()
        ),
        Movie(
            3,
            "Data 3",
            "Data 3",
            3.0,
            "Data 3",
            listOf()
        )
    )

    val dummyTvShows = listOf(
        TvShow(
            1,
            "Data 1",
            "Data 1",
            1.0,
            "Data 1",
            listOf()
        ),
        TvShow(
            2,
            "Data 2",
            "Data 2",
            2.0,
            "Data 2",
            listOf()
        ),
        TvShow(
            3,
            "Data 3",
            "Data 3",
            3.0,
            "Data 3",
            listOf()
        )
    )
}