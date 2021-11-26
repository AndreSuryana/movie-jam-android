package com.example.moviejam.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.RemoteDataSource
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.Resource
import javax.inject.Inject

class MovieJamRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MainRepository {



    override suspend fun getTrendingMovies(): LiveData<Resource<MoviesResponse>> =
        remoteDataSource.getTrendingMovies()

    override suspend fun getPopularMovies(): LiveData<Resource<MoviesResponse>> =
        remoteDataSource.getPopularMovies()

    override suspend fun getPopularTvShows(): LiveData<Resource<TvShowsResponse>> =
        remoteDataSource.getPopularTvShows()

    override suspend fun getMovies(page: Int): LiveData<Resource<MoviesResponse>> =
        remoteDataSource.getMovies(page)

    override suspend fun getTvShows(page: Int): LiveData<Resource<TvShowsResponse>> =
        remoteDataSource.getTvShows(page)

    override suspend fun getMovieDetail(movieId: String): LiveData<Resource<MovieDetailResponse>> =
        remoteDataSource.getMovieDetail(movieId)

    override suspend fun getTvShowDetail(tvId: String): LiveData<Resource<TvShowDetailResponse>> =
        remoteDataSource.getTvShowDetail(tvId)

    override suspend fun searchMovies(
        query: String?,
        page: Int
    ): LiveData<Resource<MoviesResponse>> =
        remoteDataSource.searchMovies(query, page)

    override suspend fun searchTvShows(
        query: String?,
        page: Int
    ): LiveData<Resource<TvShowsResponse>> =
        remoteDataSource.searchTvShows(query, page)

    override fun getListFavoriteMovies(): LiveData<Resource<PagedList<FavoriteEntity>>> {
        return object : BoundResource<PagedList<FavoriteEntity>, List<FavoriteEntity>>() {
            override fun loadFromDB(): LiveData<PagedList<FavoriteEntity>> {
                val config = PagedList.Config.Builder()
                    .setPageSize(20)
                    .setMaxSize(100)
                    .setEnablePlaceholders(false)
                    .build()
                return LivePagedListBuilder(localDataSource.getListFavoriteMovies(), config).build()
            }
        }.asLiveData()
    }

    override fun getListFavoriteTvShows(): LiveData<Resource<PagedList<FavoriteEntity>>> {
        return object : BoundResource<PagedList<FavoriteEntity>, List<FavoriteEntity>>() {
            override fun loadFromDB(): LiveData<PagedList<FavoriteEntity>> {
                val config = PagedList.Config.Builder()
                    .setPageSize(20)
                    .setMaxSize(100)
                    .setEnablePlaceholders(false)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getListFavoriteTvShows(),
                    config
                ).build()
            }
        }.asLiveData()
    }

    override suspend fun checkFavoriteMovies(id: Int?): Int? =
        localDataSource.checkFavoriteMovies(id)

    override suspend fun checkFavoriteTvShows(id: Int?): Int? =
        localDataSource.checkFavoriteTvShows(id)

    override suspend fun insertToFavorites(fav: FavoriteEntity) =
        localDataSource.insertToFavorites(fav)

    override suspend fun deleteFromFavorites(id: Int?) =
        localDataSource.deleteFromFavorites(id)
}