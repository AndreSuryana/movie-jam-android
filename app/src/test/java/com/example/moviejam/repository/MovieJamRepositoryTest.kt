package com.example.moviejam.repository

import androidx.paging.PagingData
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieJamRepositoryTest {

    private val dummyMoviesResponse = DummyData.dummyMoviesResponse
    private val dummyTvShowsResponse = DummyData.dummyTvShowsResponse
    private val dummyMovieDetail = DummyData.dummyDetailMovie
    private val dummyTvShowDetail = DummyData.dummyDetailTvShow

    @Mock
    private lateinit var fakeRepository: FakeRepository

    @Test
    fun getTrendingMovies() = runBlockingTest {
        `when`(fakeRepository.getTrendingMovies()).thenReturn(Resource.success(dummyMoviesResponse))
        val trendingMoviesResult = fakeRepository.getTrendingMovies()
        verify(fakeRepository).getTrendingMovies()

        assertThat(trendingMoviesResult).isNotNull()
        assertThat(trendingMoviesResult.data).isEqualTo(dummyMoviesResponse)
    }

    @Test
    fun getPopularMovies() = runBlockingTest {
        `when`(fakeRepository.getPopularMovies()).thenReturn(Resource.success(dummyMoviesResponse))
        val popularMoviesResult = fakeRepository.getPopularMovies()
        verify(fakeRepository).getPopularMovies()

        assertThat(popularMoviesResult).isNotNull()
        assertThat(popularMoviesResult.data).isEqualTo(dummyMoviesResponse)
    }

    @Test
    fun getPopularTvShows() = runBlockingTest {
        `when`(fakeRepository.getPopularTvShows()).thenReturn(Resource.success(dummyTvShowsResponse))
        val popularTvShowsResult = fakeRepository.getPopularTvShows()
        verify(fakeRepository).getPopularTvShows()

        assertThat(popularTvShowsResult).isNotNull()
        assertThat(popularTvShowsResult.data).isEqualTo(dummyTvShowsResponse)
    }

    @Test
    fun getMovies() = runBlockingTest {
        @Suppress("UNCHECKED_CAST")
        val dataSourceFactory = mock(PagingData::class.java) as PagingData<Movie>
        `when`(fakeRepository.getMovies()).thenReturn(dataSourceFactory)

    }

    @Test
    fun getTvShows() = runBlockingTest {

    }

    @Test
    fun getMovieDetail() = runBlockingTest {
        val id = "1"
        `when`(fakeRepository.getMovieDetail(id)).thenReturn(Resource.success(dummyMovieDetail))
        val movieDetailResult = fakeRepository.getMovieDetail(id)
        verify(fakeRepository).getMovieDetail(id)

        assertThat(movieDetailResult).isNotNull()
        assertThat(movieDetailResult.data).isEqualTo(dummyMovieDetail)
    }

    @Test
    fun getTvShowDetail() = runBlockingTest {
        val id = "1"
        `when`(fakeRepository.getTvShowDetail(id)).thenReturn(Resource.success(dummyTvShowDetail))
        val tvShowDetailResult = fakeRepository.getTvShowDetail(id)
        verify(fakeRepository).getTvShowDetail(id)

        assertThat(tvShowDetailResult).isNotNull()
        assertThat(tvShowDetailResult.data).isEqualTo(dummyTvShowDetail)
    }
}