package com.example.moviejam.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.remote.response.movie.MoviesResponse
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
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<MoviesResponse>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private lateinit var viewModel: MoviesViewModel

    private val dummyMoviesResponse = DummyData.dummyMoviesResponse
    private val dummyEmptyMoviesResponse = DummyData.dummyEmptyMoviesResponse
    private val startingPage = 1

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getMovies should be success`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyMoviesResponse)

        `when`(fakeMovieJamRepository.getMovies(startingPage)).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.movies).isEqualTo(expectedValue?.data?.movies)
        assertThat(actualValue?.data?.movies?.size).isEqualTo(expectedValue?.data?.movies?.size)
    }

    @Test
    fun `getTopMovies should be success but data is empty`() = runBlockingTest {
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyEmptyMoviesResponse)

        `when`(fakeMovieJamRepository.getMovies(startingPage)).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyMoviesResponse.movies.size
        val actualMoviesSize = viewModel.getMovies().value?.data?.movies?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `getTopMovies should be error`() = runBlockingTest {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getMovies(startingPage)).thenReturn(expected)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getMovies().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun `searchMovies should be success`() = runBlockingTest {
        val query = "Venom"

        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyMoviesResponse)

        `when`(fakeMovieJamRepository.searchMovies(query, startingPage)).thenReturn(expected)

        viewModel.searchMovies(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.searchMovies(query).value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.movies).isEqualTo(expectedValue?.data?.movies)
        assertThat(actualValue?.data?.movies?.size).isEqualTo(expectedValue?.data?.movies?.size)
    }

    @Test
    fun `searchMovies should be success but data is empty`() = runBlockingTest {
        val query = "Sample Title"

        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.success(dummyEmptyMoviesResponse)

        `when`(fakeMovieJamRepository.searchMovies(query, startingPage)).thenReturn(expected)

        viewModel.searchMovies(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedMoviesSize = dummyEmptyMoviesResponse.movies.size
        val actualMoviesSize = viewModel.searchMovies(query).value?.data?.movies?.size

        assertThat(actualMoviesSize).isEqualTo(expectedMoviesSize)
    }

    @Test
    fun `searchMovies should be error`() = runBlockingTest {
        val query = "Venom"

        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<MoviesResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.searchMovies(query, startingPage)).thenReturn(expected)

        viewModel.searchMovies(query).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.searchMovies(query).value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }
}