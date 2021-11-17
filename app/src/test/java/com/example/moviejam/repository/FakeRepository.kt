package com.example.moviejam.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.GenresItem as MovieGenresItem
import com.example.moviejam.data.source.remote.response.tvshowdetail.GenresItem as TvShowGenresItem
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.moviedetail.ProductionCompaniesItem as MovieProductionCompaniesItem
import com.example.moviejam.data.source.remote.response.tvshowdetail.ProductionCompaniesItem as TvShowProductionCompaniesItem
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.Resource

class FakeRepository : MainRepository {

    private val topMovies = mutableListOf<Movie>()
    private val popularMovies = mutableListOf<Movie>()
    private val popularTvShows = mutableListOf<TvShow>()
    private val movies = mutableListOf<Movie>()
    private val tvShows = mutableListOf<TvShow>()

    private val observableTopMovies = MutableLiveData<List<Movie>>(topMovies)
    private val observablePopularMovies = MutableLiveData<List<Movie>>(popularMovies)
    private val observablePopularTvShows = MutableLiveData<List<TvShow>>(popularTvShows)
    private val observableMovies = MutableLiveData<List<Movie>>(movies)
    private val observableTvShows = MutableLiveData<List<TvShow>>(tvShows)

    fun setTopMoviesList(list: List<Movie>) {
        topMovies.addAll(list)
        refreshLiveDataList()
    }

    fun setPopularMoviesList(list: List<Movie>) {
        popularMovies.addAll(list)
        refreshLiveDataList()
    }

    fun setPopularTvShowsList(list: List<TvShow>) {
        popularTvShows.addAll(list)
        refreshLiveDataList()
    }

    fun setMoviesList(list: List<Movie>) {
        movies.addAll(list)
        refreshLiveDataList()
    }

    fun setTvShowsList(list: List<TvShow>) {
        tvShows.addAll(list)
        refreshLiveDataList()
    }

    private fun refreshLiveDataList() {
        observableTopMovies.postValue(topMovies)
        observablePopularMovies.postValue(popularMovies)
        observablePopularTvShows.postValue(popularTvShows)
        observableMovies.postValue(movies)
        observableTvShows.postValue(tvShows)
    }

    override suspend fun getTrendingMovies(): Resource<MoviesResponse> {
        val result = MoviesResponse(
            1,
            1,
            topMovies,
            topMovies.size
        )
        return if (result.movies.isNotEmpty())
            Resource.success(result)
        else
            Resource.error("Error!")
    }

    override suspend fun getPopularMovies(): Resource<MoviesResponse> {
        val result = MoviesResponse(
            1,
            1,
            popularMovies,
            popularMovies.size
        )
        return if (result.movies.isNotEmpty())
            Resource.success(result)
        else
            Resource.error("Error!")
    }

    override suspend fun getPopularTvShows(): Resource<TvShowsResponse> {
        val result = TvShowsResponse(
            1,
            1,
            popularTvShows,
            popularTvShows.size
        )
        return if (result.tvShows.isNotEmpty())
            Resource.success(result)
        else
            Resource.error("Error!")
    }

    override suspend fun getMovies(): Resource<MoviesResponse> {
        val result = MoviesResponse(
            1,
            1,
            movies,
            movies.size
        )
        return if (result.movies.isNotEmpty())
            Resource.success(result)
        else
            Resource.error("Error!")
    }

    override suspend fun getTvShows(): Resource<TvShowsResponse> {
        val result = TvShowsResponse(
            1,
            1,
            tvShows,
            tvShows.size
        )
        return if (result.tvShows.isNotEmpty())
            Resource.success(result)
        else
            Resource.error("Error!")
    }

    override suspend fun getMovieDetail(movieId: String): Resource<MovieDetailResponse> {
        val result = MovieDetailResponse(
            1,
            "Data 1",
            "Data 1",
            "Data 1",
            1.0,
            "Data 1",
            genres = listOf(
                MovieGenresItem("Genre 1", 1),
                MovieGenresItem("Genre 2", 2)
            ),
            "Data 1",
            "Data 1",
            "Data 1",
            productionCompanies = listOf(
                MovieProductionCompaniesItem("Company 1", "Company 1", 1, "Company 1")
            )
        )
        return Resource.success(result)
    }

    override suspend fun getTvShowDetail(tvId: String): Resource<TvShowDetailResponse> {
        val result = TvShowDetailResponse(
            1,
            "Data 1",
            "Data 1",
            "Data 1",
            1.0,
            "Data 1",
            genres = listOf(
                TvShowGenresItem("Genre 1", 1),
                TvShowGenresItem("Genre 2", 2)
            ),
            "Data 1",
            "Data 1",
            "Data 1",
            productionCompanies = listOf(
                TvShowProductionCompaniesItem("Company 1", "Company 1", 1, "Company 1")
            )
        )
        return Resource.success(result)
    }
}