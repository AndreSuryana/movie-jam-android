package com.example.moviejam.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.example.moviejam.getOrAwaitListTest
import com.example.moviejam.repository.FakeRepository
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
    fun `observe movies`() {
        mainCoroutineScope.launch {
            val value = moviesViewModel.setMovies().getOrAwaitListTest()
            assertThat(value).isNotNull()
        }
    }

    @Test
    fun `search movies`() {
        mainCoroutineScope.launch {
            val query = "Naruto"
            val value = moviesViewModel.searchMovies(query).getOrAwaitListTest()
            assertThat(value).isNotNull()
        }
    }
}