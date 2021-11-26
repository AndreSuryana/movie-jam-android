package com.example.moviejam.ui.main.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviejam.data.repository.MainRepository
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
class TvShowsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<TvShowsResponse>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private lateinit var viewModel: TvShowsViewModel

    private val dummyTvShowsResponse = DummyData.dummyTvShowsResponse
    private val dummyEmptyTvShowsResponse = DummyData.dummyEmptyTvShowsResponse
    private val startingPage = 1

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getTvShows should be success`() = runBlockingTest {
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyTvShowsResponse)

        `when`(fakeMovieJamRepository.getTvShows(startingPage)).thenReturn(expected)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTvShows().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.tvShows).isEqualTo(expectedValue?.data?.tvShows)
        assertThat(actualValue?.data?.tvShows?.size).isEqualTo(expectedValue?.data?.tvShows?.size)
    }

    @Test
    fun `getTvShows should be success but data is empty`() = runBlockingTest {
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyEmptyTvShowsResponse)

        `when`(fakeMovieJamRepository.getTvShows(startingPage)).thenReturn(expected)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyTvShowsResponse.tvShows.size
        val actualMoviesSize = viewModel.getTvShows().value?.data?.tvShows?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `getTvShows should be error`() = runBlockingTest {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getTvShows(startingPage)).thenReturn(expected)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getTvShows().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun `searchTvShows should be success`() = runBlockingTest {
        val query = "Venom"

        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyTvShowsResponse)

        `when`(fakeMovieJamRepository.searchTvShows(query, startingPage)).thenReturn(expected)

        viewModel.searchTvShows(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.searchTvShows(query).value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.tvShows).isEqualTo(expectedValue?.data?.tvShows)
        assertThat(actualValue?.data?.tvShows?.size).isEqualTo(expectedValue?.data?.tvShows?.size)
    }

    @Test
    fun `searchMovies should be success but data is empty`() = runBlockingTest {
        val query = "Sample Title"

        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.success(dummyEmptyTvShowsResponse)

        `when`(fakeMovieJamRepository.searchTvShows(query, startingPage)).thenReturn(expected)

        viewModel.searchTvShows(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyTvShowsResponse.tvShows.size
        val actualMoviesSize = viewModel.searchTvShows(query).value?.data?.tvShows?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `searchMovies should be error`() = runBlockingTest {
        val query = "Venom"

        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<TvShowsResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.searchTvShows(query, startingPage)).thenReturn(expected)

        viewModel.searchTvShows(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.searchTvShows(query).value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }
}