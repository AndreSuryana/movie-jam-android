package com.example.moviejam.ui.main.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.example.moviejam.getOrAwaitListTest
import com.example.moviejam.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvShowsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScope = MainCoroutineRule()

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        tvShowsViewModel = TvShowsViewModel(fakeRepository)
    }

    @Test
    fun `observe tv shows`() {
        mainCoroutineScope.launch {
            val value = tvShowsViewModel.setTvShows().getOrAwaitListTest()
            assertThat(value).isNotNull()
        }
    }

    @Test
    fun `search tv shows`() {
        mainCoroutineScope.launch {
            val query = "Naruto"
            val value = tvShowsViewModel.searchTvShows(query)
            assertThat(value).isNotNull()
        }
    }
}