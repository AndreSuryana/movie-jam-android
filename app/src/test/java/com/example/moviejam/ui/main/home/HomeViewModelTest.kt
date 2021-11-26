package com.example.moviejam.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
import com.example.moviejam.data.source.remote.response.tvshow.TvShowsResponse
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesObserver: Observer<Resource<MoviesResponse>>

    @Mock
    private lateinit var tvShowsObserver: Observer<Resource<TvShowsResponse>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private lateinit var viewModel: HomeViewModel

    private val dummyMoviesResponse = DummyData.dummyMoviesResponse
    private val dummyTvShowsResponse = DummyData.dummyTvShowsResponse
    private val dummyEmptyMoviesResponse = DummyData.dummyEmptyMoviesResponse
    private val dummyEmptyTvShowsResponse = DummyData.dummyEmptyTvShowsResponse

    @Before
    fun setUp() {
        viewModel = HomeViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getTopMovies should be success`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyMoviesResponse)

        `when`(fakeMovieJamRepository.getTrendingMovies()).thenReturn(expected)

        viewModel.getTopMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTopMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.movies).isEqualTo(expectedValue?.data?.movies)
        assertThat(actualValue?.data?.movies?.size).isEqualTo(expectedValue?.data?.movies?.size)
    }

    @Test
    fun `getTopMovies should be success but data is empty`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyEmptyMoviesResponse)

        `when`(fakeMovieJamRepository.getTrendingMovies()).thenReturn(expected)

        viewModel.getTopMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyMoviesResponse.movies.size
        val actualMoviesSize = viewModel.getTopMovies().value?.data?.movies?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `getTopMovies should be error`() = runBlockingTest {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getTrendingMovies()).thenReturn(expected)

        viewModel.getTopMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val actualMessage = viewModel.getTopMovies().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun `getPopularMovies should be success`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyMoviesResponse)

        `when`(fakeMovieJamRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getPopularMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getPopularMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.movies).isEqualTo(expectedValue?.data?.movies)
        assertThat(actualValue?.data?.movies?.size).isEqualTo(expectedValue?.data?.movies?.size)
    }

    @Test
    fun `getPopularMovies should be success but data is empty`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyEmptyMoviesResponse)

        `when`(fakeMovieJamRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getPopularMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyMoviesResponse.movies.size
        val actualMoviesSize = viewModel.getPopularMovies().value?.data?.movies?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `getPopularMovies should be error`() = runBlockingTest {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getPopularMovies()).thenReturn(expected)

        viewModel.getPopularMovies().observeForever(moviesObserver)
        verify(moviesObserver).onChanged(expected.value)

        val actualMessage = viewModel.getPopularMovies().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun `getPopularTvShows should be success`() = runBlockingTest {
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyTvShowsResponse)

        `when`(fakeMovieJamRepository.getPopularTvShows()).thenReturn(expected)

        viewModel.getPopularTvShows().observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getPopularTvShows().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.tvShows).isEqualTo(expectedValue?.data?.tvShows)
        assertThat(actualValue?.data?.tvShows?.size).isEqualTo(expectedValue?.data?.tvShows?.size)
    }

    @Test
    fun `getPopularTvShows should be success but data is empty`() = runBlockingTest {
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyEmptyTvShowsResponse)

        `when`(fakeMovieJamRepository.getPopularTvShows()).thenReturn(expected)

        viewModel.getPopularTvShows().observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyTvShowsResponse.tvShows.size
        val actualMoviesSize = viewModel.getPopularTvShows().value?.data?.tvShows?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `getPopularTvShows should be error`() = runBlockingTest {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getPopularTvShows()).thenReturn(expected)

        viewModel.getPopularTvShows().observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(expected.value)

        val actualMessage = viewModel.getPopularTvShows().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }
}