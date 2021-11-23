package com.example.moviejam.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.moviejam.paging.MoviesPagingSource
import com.example.moviejam.paging.SearchMoviesPagingSource
import com.example.moviejam.paging.SearchTvShowsPagingSource
import com.example.moviejam.paging.TvShowsPagingSource
import com.example.moviejam.data.source.remote.MovieJamAPI
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.EspressoIdlingResources
import com.example.moviejam.utils.Resource
import javax.inject.Inject

class MovieJamRepository @Inject constructor(
    private val api: MovieJamAPI
) : MainRepository {

    override suspend fun getTrendingMovies(): Resource<MoviesResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getTrendingMovies()
            val result = response.body()
            EspressoIdlingResources.decrement()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message())
        } catch (e: Exception) {
            Resource.error(e.message ?: "An error occurred!")
        }
    }

    override suspend fun getPopularMovies(): Resource<MoviesResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getTopRatedMovies()
            val result = response.body()
            EspressoIdlingResources.decrement()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message())
        } catch (e: Exception) {
            Resource.error(e.message ?: "An error occurred!")
        }
    }

    override suspend fun getPopularTvShows(): Resource<TvShowsResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getTopRatedTvShows()
            val result = response.body()
            EspressoIdlingResources.decrement()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message())
        } catch (e: Exception) {
            Resource.error(e.message ?: "An error occurred!")
        }
    }

    override suspend fun getMovies(): LiveData<PagingData<Movie>> {
        EspressoIdlingResources.increment()
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(api) }
        ).liveData
        EspressoIdlingResources.decrement()
        return pager
    }

    override suspend fun getTvShows(): LiveData<PagingData<TvShow>> {
        EspressoIdlingResources.increment()
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TvShowsPagingSource(api) }
        ).liveData
        EspressoIdlingResources.decrement()
        return pager
    }

    override suspend fun getMovieDetail(movieId: String): Resource<MovieDetailResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getMovieDetail(movieId)
            val result = response.body()
            EspressoIdlingResources.decrement()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message())
        } catch (e: Exception) {
            Resource.error(e.message ?: "An error occurred!")
        }
    }

    override suspend fun getTvShowDetail(tvId: String): Resource<TvShowDetailResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getTvShowDetail(tvId)
            val result = response.body()
            EspressoIdlingResources.decrement()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message())
        } catch (e: Exception) {
            Resource.error(e.message ?: "An error occurred!")
        }
    }

    override suspend fun searchMovies(query: String?): LiveData<PagingData<Movie>> {
        EspressoIdlingResources.increment()
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(api, query) }
        ).liveData
        EspressoIdlingResources.decrement()
        return pager
    }

    override suspend fun searchTvShows(query: String?): LiveData<PagingData<TvShow>> {
        EspressoIdlingResources.increment()
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchTvShowsPagingSource(api, query) }
        ).liveData
        EspressoIdlingResources.decrement()
        return pager
    }
}