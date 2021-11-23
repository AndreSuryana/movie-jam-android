package com.example.moviejam.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.Resource

interface MainRepository {

    suspend fun getTrendingMovies(): Resource<MoviesResponse>
    suspend fun getPopularMovies(): Resource<MoviesResponse>
    suspend fun getPopularTvShows(): Resource<TvShowsResponse>
    suspend fun getMovies(): LiveData<PagingData<Movie>>
    suspend fun getTvShows(): LiveData<PagingData<TvShow>>
    suspend fun getMovieDetail(movieId: String): Resource<MovieDetailResponse>
    suspend fun getTvShowDetail(tvId: String): Resource<TvShowDetailResponse>
    suspend fun searchMovies(query: String?): LiveData<PagingData<Movie>>
    suspend fun searchTvShows(query: String?): LiveData<PagingData<TvShow>>
}