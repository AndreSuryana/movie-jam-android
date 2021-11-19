package com.example.moviejam.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.example.moviejam.getOrAwaitListTest
import com.example.moviejam.repository.FakeRepository
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScope = MainCoroutineRule()

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        moviesViewModel = MoviesViewModel(fakeRepository)
    }

    @Test
    fun `observe movie list, where movie list is empty, return status ERROR`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setMoviesList(emptyList())

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            moviesViewModel.setMovies()
        }

        val value = moviesViewModel.listMovies.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `observe movie list, where movie list is not empty, return status SUCCESS`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setMoviesList(DummyData.dummyMovies)

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            moviesViewModel.setMovies()
        }

        val value = moviesViewModel.listMovies.getOrAwaitListTest()
        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `ensure the number of movie list as expected`() {

        // Set movie list in fake repository
        // Which has 3 data
        fakeRepository.setMoviesList(DummyData.dummyMovies)

        // Set value of listMovies in ViewModel
        mainCoroutineScope.launch {
            moviesViewModel.setMovies()
        }

        val size = moviesViewModel.listMovies.getOrAwaitListTest().getDataIfNotHandledYet()?.data?.size

        assertThat(size).isEqualTo(3)
    }
}