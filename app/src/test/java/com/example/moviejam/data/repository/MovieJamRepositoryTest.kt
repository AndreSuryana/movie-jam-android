package com.example.moviejam.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.RemoteDataSource
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.LiveDataTestUtil
import com.example.moviejam.utils.PagedListUtil
import com.example.moviejam.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieJamRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)

    private val fakeMovieJamRepository = FakeMovieJamRepository(remote, local)

    private val dummyMoviesResponse = DummyData.dummyMoviesResponse
    private val dummyTvShowsResponse = DummyData.dummyTvShowsResponse
    private val dummyMovieDetailResponse = DummyData.dummyDetailMovie
    private val dummyTvShowDetailResponse = DummyData.dummyDetailTvShow
    private val dummyFavoriteMovies = DummyData.dummyFavoriteMovies
    private val dummyFavoriteTvShows = DummyData.dummyFavoriteTvShows
    private val startingPage = 1

    @Test
    fun getTrendingMovies() = runBlockingTest {
        val dummyMovies = MutableLiveData<Resource<MoviesResponse>>()
        dummyMovies.value = Resource.success(dummyMoviesResponse)
        `when`(remote.getTrendingMovies()).thenReturn(dummyMovies)

        val trendingMovies = LiveDataTestUtil.getValue(fakeMovieJamRepository.getTrendingMovies())
        verify(remote).getTrendingMovies()

        assertThat(trendingMovies.data).isNotNull()
        assertThat(trendingMovies.data?.movies).isEqualTo(dummyMoviesResponse.movies)
    }

    @Test
    fun getPopularMovies() = runBlockingTest {
        val dummyMovies = MutableLiveData<Resource<MoviesResponse>>()
        dummyMovies.value = Resource.success(dummyMoviesResponse)
        `when`(remote.getPopularMovies()).thenReturn(dummyMovies)

        val popularMovies = LiveDataTestUtil.getValue(fakeMovieJamRepository.getPopularMovies())
        verify(remote).getPopularMovies()

        assertThat(popularMovies.data).isNotNull()
        assertThat(popularMovies.data?.movies).isEqualTo(dummyMoviesResponse.movies)
    }

    @Test
    fun getPopularTvShows() = runBlockingTest {
        val dummyTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        dummyTvShows.value = Resource.success(dummyTvShowsResponse)
        `when`(remote.getPopularTvShows()).thenReturn(dummyTvShows)

        val popularTvShows = LiveDataTestUtil.getValue(fakeMovieJamRepository.getPopularTvShows())
        verify(remote).getPopularTvShows()

        assertThat(popularTvShows.data).isNotNull()
        assertThat(popularTvShows.data?.tvShows).isEqualTo(dummyTvShowsResponse.tvShows)
    }

    @Test
    fun getMovies() = runBlockingTest {
        val dummyMovies = MutableLiveData<Resource<MoviesResponse>>()
        dummyMovies.value = Resource.success(dummyMoviesResponse)
        `when`(remote.getMovies(startingPage)).thenReturn(dummyMovies)

        val popularMovies =
            LiveDataTestUtil.getValue(fakeMovieJamRepository.getMovies(startingPage))
        verify(remote).getMovies(startingPage)

        assertThat(popularMovies.data).isNotNull()
        assertThat(popularMovies.data?.movies).isEqualTo(dummyMoviesResponse.movies)
    }

    @Test
    fun getTvShows() = runBlockingTest {
        val dummyTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        dummyTvShows.value = Resource.success(dummyTvShowsResponse)
        `when`(remote.getTvShows(startingPage)).thenReturn(dummyTvShows)

        val popularTvShows =
            LiveDataTestUtil.getValue(fakeMovieJamRepository.getTvShows(startingPage))
        verify(remote).getTvShows(startingPage)

        assertThat(popularTvShows.data).isNotNull()
        assertThat(popularTvShows.data?.tvShows).isEqualTo(dummyTvShowsResponse.tvShows)
    }

    @Test
    fun getMovieDetail() = runBlockingTest {
        val id = "1"
        val dummyMovieDetail = MutableLiveData<Resource<MovieDetailResponse>>()
        dummyMovieDetail.value = Resource.success(dummyMovieDetailResponse)
        `when`(remote.getMovieDetail(id)).thenReturn(dummyMovieDetail)

        val movieDetail = LiveDataTestUtil.getValue(fakeMovieJamRepository.getMovieDetail(id))
        verify(remote).getMovieDetail(id)

        assertThat(movieDetail.data).isNotNull()
        assertThat(movieDetail.data).isEqualTo(dummyMovieDetailResponse)
    }

    @Test
    fun getTvShowDetail() = runBlockingTest {
        val id = "1"
        val dummyTvShowDetail = MutableLiveData<Resource<TvShowDetailResponse>>()
        dummyTvShowDetail.value = Resource.success(dummyTvShowDetailResponse)
        `when`(remote.getTvShowDetail(id)).thenReturn(dummyTvShowDetail)

        val tvShowDetail = LiveDataTestUtil.getValue(fakeMovieJamRepository.getTvShowDetail(id))
        verify(remote).getTvShowDetail(id)

        assertThat(tvShowDetail.data).isNotNull()
        assertThat(tvShowDetail.data).isEqualTo(dummyTvShowDetailResponse)
    }

    @Test
    fun searchMovies() = runBlockingTest {
        val query = "Venom"
        val dummyMovies = MutableLiveData<Resource<MoviesResponse>>()
        dummyMovies.value = Resource.success(dummyMoviesResponse)
        `when`(remote.searchMovies(query, startingPage)).thenReturn(dummyMovies)

        val popularMovies =
            LiveDataTestUtil.getValue(fakeMovieJamRepository.searchMovies(query, startingPage))
        verify(remote).searchMovies(query, startingPage)

        assertThat(popularMovies.data).isNotNull()
        assertThat(popularMovies.data?.movies).isEqualTo(dummyMoviesResponse.movies)
    }

    @Test
    fun searchTvShows() = runBlockingTest {
        val query = "Chucky"
        val dummyTvShows = MutableLiveData<Resource<TvShowsResponse>>()
        dummyTvShows.value = Resource.success(dummyTvShowsResponse)
        `when`(remote.searchTvShows(query, startingPage)).thenReturn(dummyTvShows)

        val popularTvShows =
            LiveDataTestUtil.getValue(fakeMovieJamRepository.searchTvShows(query, startingPage))
        verify(remote).searchTvShows(query, startingPage)

        assertThat(popularTvShows.data).isNotNull()
        assertThat(popularTvShows.data?.tvShows).isEqualTo(dummyTvShowsResponse.tvShows)
    }

    @Test
    fun getListFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteMovies()

        val favoriteMovies = Resource.success(PagedListUtil.mockPagedList(dummyFavoriteMovies))
        verify(local).getListFavoriteMovies()

        assertThat(favoriteMovies.data).isNotNull()
        assertThat(favoriteMovies.data?.size).isEqualTo(dummyFavoriteMovies.size)
    }

    @Test
    fun getListFavoriteTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getListFavoriteTvShows()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteTvShows()

        val favoriteTvShows = Resource.success(PagedListUtil.mockPagedList(dummyFavoriteTvShows))
        verify(local).getListFavoriteTvShows()

        assertThat(favoriteTvShows.data).isNotNull()
        assertThat(favoriteTvShows.data?.size).isEqualTo(dummyFavoriteMovies.size)
    }

    @Test
    fun checkFavoriteMovies() = runBlockingTest {
        val id = 1
        val mockedReturnValue = 1
        `when`(local.checkFavoriteMovies(id)).thenReturn(mockedReturnValue)

        val returnValue = fakeMovieJamRepository.checkFavoriteMovies(id)
        verify(local).checkFavoriteMovies(id)

        assertThat(returnValue).isNotNull()
        assertThat(returnValue).isEqualTo(mockedReturnValue)
    }

    @Test
    fun checkFavoriteTvShows() = runBlockingTest {
        val id = 1
        val mockedReturnValue = 1

        `when`(local.checkFavoriteTvShows(id)).thenReturn(mockedReturnValue)

        val returnValue = fakeMovieJamRepository.checkFavoriteTvShows(id)
        verify(local).checkFavoriteTvShows(id)

        assertThat(returnValue).isNotNull()
        assertThat(returnValue).isEqualTo(mockedReturnValue)
    }

    @Test
    fun insertToFavorites() = runBlockingTest {
        val favoriteMovie = FavoriteEntity(
            580489,
            "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
            "Venom: Let There Be Carnage",
            6.8,
            "2021-09-30",
            true
        )
        val favoriteList = listOf(favoriteMovie)

        fakeMovieJamRepository.insertToFavorites(favoriteMovie)

        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteMovies()

        val favoriteMovies = Resource.success(PagedListUtil.mockPagedList(favoriteList))
        verify(local).getListFavoriteMovies()

        assertThat(favoriteMovies.data).isNotNull()
        assertThat(favoriteMovies.data?.size).isEqualTo(favoriteList.size)
    }

    @Test
    fun deleteFromFavorites() = runBlockingTest {
        val id = 580489
        val listFavorites = ArrayList<FavoriteEntity>(dummyFavoriteMovies)

        fakeMovieJamRepository.deleteFromFavorites(id)
        listFavorites.removeIf { it.id == id }

        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteMovies()

        val favoriteMovies = Resource.success(PagedListUtil.mockPagedList(listFavorites))
        verify(local).getListFavoriteMovies()

        assertThat(favoriteMovies.data).isNotNull()
        assertThat(favoriteMovies.data?.size).isEqualTo(listFavorites.size)
    }
}