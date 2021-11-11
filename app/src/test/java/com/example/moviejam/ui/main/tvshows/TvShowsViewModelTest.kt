package com.example.moviejam.ui.main.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.getOrAwaitListTest
import com.example.moviejam.repository.FakeRepository
import com.example.moviejam.utils.Status
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

    private lateinit var tvSHowsViewModel: TvShowsViewModel
    private lateinit var fakeRepository: FakeRepository

    // Dummy data
    private val data1 = DataEntity(
        1,
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1",
        "Data1"
    )
    private val data2 = DataEntity(
        2,
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2",
        "Data2"
    )
    private val data3 = DataEntity(
        3,
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3",
        "Data3"
    )

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        tvSHowsViewModel = TvShowsViewModel(fakeRepository)
    }

    @Test
    fun `observe tv show list, where tv show list is empty, return status ERROR`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setTvShowsList(emptyList())

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            tvSHowsViewModel.setTvShows(fakeRepository.getMovies())
        }

        val value = tvSHowsViewModel.listTvShows.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `observe movie list, where movie list is not empty, return status SUCCESS`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setMoviesList(listOf(data1, data2, data3))

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            tvSHowsViewModel.setTvShows(fakeRepository.getMovies())
        }

        val value = tvSHowsViewModel.listTvShows.getOrAwaitListTest()
        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `ensure the number of movie list as expected`() {

        // Set movie list in fake repository
        // Which has 3 data
        fakeRepository.setTvShowsList(listOf(data1, data2, data3))

        // Set value of listMovies in ViewModel
        mainCoroutineScope.launch {
            tvSHowsViewModel.setTvShows(fakeRepository.getTvShows())
        }

        val size = tvSHowsViewModel.listTvShows.getOrAwaitListTest().getDataIfNotHandledYet()?.data?.size

        assertThat(size).isEqualTo(3)
    }
}