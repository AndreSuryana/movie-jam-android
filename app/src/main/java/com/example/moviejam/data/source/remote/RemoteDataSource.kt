package com.example.moviejam.data.source.remote

import androidx.lifecycle.LiveData
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.Resource

interface RemoteDataSource {

    suspend fun getTrendingMovies(): LiveData<Resource<MoviesResponse>>
    suspend fun getPopularMovies(): LiveData<Resource<MoviesResponse>>
    suspend fun getPopularTvShows(): LiveData<Resource<TvShowsResponse>>
    suspend fun getMovies(page: Int): LiveData<Resource<MoviesResponse>>
    suspend fun getTvShows(page: Int): LiveData<Resource<TvShowsResponse>>
    suspend fun getMovieDetail(movieId: String): LiveData<Resource<MovieDetailResponse>>
    suspend fun getTvShowDetail(tvId: String): LiveData<Resource<TvShowDetailResponse>>
    suspend fun searchMovies(query: String?, page: Int): LiveData<Resource<MoviesResponse>>
    suspend fun searchTvShows(query: String?, page: Int): LiveData<Resource<TvShowsResponse>>
}