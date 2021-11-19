package com.example.moviejam.utils

import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse

object DummyData {

    val dummyMovies = listOf(
        Movie(
            580489,
            "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
            "Venom: Let There Be Carnage",
            6.8,
            "2021-09-30"
        ),
        Movie(
            522402,
            "/jKuDyqx7jrjiR9cDzB5pxzhJAdv.jpg",
            "Finch",
            8.2,
            "2021-11-04"
        ),
        Movie(
            585245,
            "",
            "/ygPTrycbMSFDc5zUpy4K5ZZtQSC.jpg",
            8.0,
            "2021-11-10"
        )
    )

    val dummyTvShows = listOf(
        TvShow(
            90462,
            "/iF8ai2QLNiHV4anwY1TuSGZXqfN.jpg",
            "Chucky",
            8.0,
            "2021-10-12"
        ),
        TvShow(
            2051,
            "/6m4uYFAJwkanZXd0n0HUQ0lYHLl.jpg",
            "The Price Is Right",
            6.9,
            "1972-09-04"
        ),
        TvShow(
            1991,
            "/oC9SgtJTDCEpWnTBtVGoAvjl5hb.jpg",
            "Rachael Ray",
            5.3,
            "2006-09-18"
        )
    )

    val dummyMoviesResponse = MoviesResponse(
        page = 1,
        totalPages = 1,
        movies = dummyMovies,
        totalResults = dummyMovies.size
    )

    val dummyTvShowsResponse = TvShowsResponse(
        page = 1,
        totalPages = 1,
        tvShows = dummyTvShows,
        totalResults = dummyTvShows.size
    )

    val dummyDetailMovie = MovieDetailResponse(
        238,
        "/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg",
        "/eEslKSwcqmiNS6va24Pbxf2UKmJ.jpg",
        "The Godfather",
        8.7,
        "1972-03-14",
        listOf(),
        "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
        "Released",
        "en",
        listOf()
    )

    val dummyDetailTvShow = TvShowDetailResponse(
        83095,
        "/qSgBzXdu6QwVVeqOYOlHolkLRxZ.jpg",
        "/6cXf5EDwVhsRv8GlBzUTVnWuk8Z.jpg",
        "The Rising of the Shield Hero",
        9.0,
        "2019-01-09",
        listOf(),
        "Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins...",
        "Returning Series",
        "ja",
        listOf()
    )
}