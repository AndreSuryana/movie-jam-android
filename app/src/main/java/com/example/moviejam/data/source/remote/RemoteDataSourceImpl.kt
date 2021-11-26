package com.example.moviejam.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviejam.data.source.remote.api.MovieJamAPI
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.EspressoIdlingResources
import com.example.moviejam.utils.Resource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val movieJamAPI: MovieJamAPI
) : RemoteDataSource {

    override suspend fun getTrendingMovies(): LiveData<Resource<MoviesResponse>> {

        EspressoIdlingResources.increment()
        val resultMovies = MutableLiveData<Resource<MoviesResponse>>()
        val response = movieJamAPI.getTrendingMovies()
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultMovies.value = result.let { Resource.success(it) }
        else if (result?.movies?.isEmpty() == true)
            resultMovies.value = Resource.error("Top Movies is Empty!")
        else
            resultMovies.value = Resource.error(response.message())

        return resultMovies
    }

    override suspend fun getPopularMovies(): LiveData<Resource<MoviesResponse>> {

        EspressoIdlingResources.increment()
        val resultMovies = MutableLiveData<Resource<MoviesResponse>>()
        val response = movieJamAPI.getTopRatedMovies()
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultMovies.value = result.let { Resource.success(it) }
        else if (result?.movies?.isEmpty() == true)
            resultMovies.value = Resource.error("Popular Movies is Empty!")
        else
            resultMovies.value = Resource.error(response.message())

        return resultMovies
    }

    override suspend fun getPopularTvShows(): LiveData<Resource<TvShowsResponse>> {

        EspressoIdlingResources.increment()
        val resultTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        val response = movieJamAPI.getTopRatedTvShows()
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultTvShows.value = result.let { Resource.success(it) }
        else if (result?.tvShows?.isEmpty() == true)
            resultTvShows.value = Resource.error("Popular TV Shows is Empty!")
        else
            resultTvShows.value = Resource.error(response.message())

        return resultTvShows
    }

    override suspend fun getMovies(page: Int): LiveData<Resource<MoviesResponse>> {

        EspressoIdlingResources.increment()
        val resultMovies = MutableLiveData<Resource<MoviesResponse>>()
        val response = movieJamAPI.getPopularMovies(page = page)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultMovies.value = result.let { Resource.success(it) }
        else if (result?.movies?.isEmpty() == true)
            resultMovies.value = Resource.error("Movies is Empty!")
        else
            resultMovies.value = Resource.error(response.message())

        return resultMovies
    }

    override suspend fun getTvShows(page: Int): LiveData<Resource<TvShowsResponse>> {

        EspressoIdlingResources.increment()
        val resultTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        val response = movieJamAPI.getPopularTvShows(page = page)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultTvShows.value = result.let { Resource.success(it) }
        else if (result?.tvShows?.isEmpty() == true)
            resultTvShows.value = Resource.error("TV Shows is Empty!")
        else
            resultTvShows.value = Resource.error(response.message())

        return resultTvShows
    }

    override suspend fun getMovieDetail(movieId: String): LiveData<Resource<MovieDetailResponse>> {

        EspressoIdlingResources.increment()
        val resultMovieDetail = MutableLiveData<Resource<MovieDetailResponse>>()
        val response = movieJamAPI.getMovieDetail(movieId)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultMovieDetail.value = result.let { Resource.success(it) }
        else if (result == null)
            resultMovieDetail.value = Resource.error("Movie Detail with id: $movieId is not found!")
        else
            resultMovieDetail.value = Resource.error(response.message())

        return resultMovieDetail
    }

    override suspend fun getTvShowDetail(tvId: String): LiveData<Resource<TvShowDetailResponse>> {

        EspressoIdlingResources.increment()
        val resultTvShowDetail = MutableLiveData<Resource<TvShowDetailResponse>>()
        val response = movieJamAPI.getTvShowDetail(tvId)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultTvShowDetail.value = result.let { Resource.success(it) }
        else if (result == null)
            resultTvShowDetail.value = Resource.error("TV Show Detail with id: $tvId is not found!")
        else
            resultTvShowDetail.value = Resource.error(response.message())

        return resultTvShowDetail
    }

    override suspend fun searchMovies(query: String?, page: Int): LiveData<Resource<MoviesResponse>> {

        EspressoIdlingResources.increment()
        val resultMovies = MutableLiveData<Resource<MoviesResponse>>()
        val response = movieJamAPI.searchMovies(query = query, page = page)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultMovies.value = result.let { Resource.success(it) }
        else if (result?.movies?.isEmpty() == true)
            resultMovies.value = Resource.error("Movies '$query' is not found!")
        else
            resultMovies.value = Resource.error(response.message())

        return resultMovies
    }

    override suspend fun searchTvShows(query: String?, page: Int): LiveData<Resource<TvShowsResponse>> {

        EspressoIdlingResources.increment()
        val resultTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        val response = movieJamAPI.searchTvShows(query = query, page = page)
        val result = response.body()
        EspressoIdlingResources.decrement()

        if (response.isSuccessful && result != null)
            resultTvShows.value = result.let { Resource.success(it) }
        else if (result?.tvShows?.isEmpty() == true)
            resultTvShows.value = Resource.error("TV Shows '$query' is not found!")
        else
            resultTvShows.value = Resource.error(response.message())

        return resultTvShows
    }
}