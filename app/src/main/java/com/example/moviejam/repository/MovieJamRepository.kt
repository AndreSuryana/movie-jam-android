package com.example.moviejam.repository

import com.example.moviejam.data.source.remote.MovieJamAPI
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
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

    override suspend fun getMovies(): Resource<MoviesResponse>{
        return try {
            EspressoIdlingResources.increment()
            val response = api.getPopularMovies()
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

    override suspend fun getTvShows(): Resource<TvShowsResponse> {
        return try {
            EspressoIdlingResources.increment()
            val response = api.getPopularTvShows()
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
}