package com.example.moviejam.data.source.remote

import com.example.moviejam.BuildConfig
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieJamAPI {

    @GET("/3/trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>

    @GET("/3/tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TvShowsResponse>

    @GET("/3/tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TvShowsResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailResponse>

    @GET("/3/tv/{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") tvId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TvShowDetailResponse>
}