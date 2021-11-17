package com.example.moviejam.repository

import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.Resource

interface MainRepository {

    suspend fun getTrendingMovies(): Resource<MoviesResponse>
    suspend fun getPopularMovies(): Resource<MoviesResponse>
    suspend fun getPopularTvShows(): Resource<TvShowsResponse>
    suspend fun getMovies(): Resource<MoviesResponse>
    suspend fun getTvShows(): Resource<TvShowsResponse>
    suspend fun getMovieDetail(movieId: String): Resource<MovieDetailResponse>
    suspend fun getTvShowDetail(tvId: String): Resource<TvShowDetailResponse>
}