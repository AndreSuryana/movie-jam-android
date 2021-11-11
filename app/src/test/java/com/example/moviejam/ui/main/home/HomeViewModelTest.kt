package com.example.moviejam.ui.main.home

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
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScope = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel
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
        homeViewModel = HomeViewModel(fakeRepository)
    }

    /**
     * Test for Top Movies List in Home View Model
     */
    @Test
    fun `observe top movie list, where list is empty, return status ERROR`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setTopMoviesList(emptyList())

        // Set value of listTopMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setTopMovies(fakeRepository.getTopMovies())
        }

        val value = homeViewModel.listTopMovies.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `observe top movie list, where list is not empty, return status SUCCESS`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setTopMoviesList(listOf(data1, data2, data3))

        // Set value of listTopMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setTopMovies(fakeRepository.getTopMovies())
        }

        val value = homeViewModel.listTopMovies.getOrAwaitListTest()
        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `ensure the number of top movie list as expected`() {

        // Set movie list in fake repository
        // Which has 3 data
        fakeRepository.setTopMoviesList(listOf(data1, data2, data3))

        // Set value of listTopMovies in ViewModel
        mainCoroutineScope.launch {
            homeViewModel.setTopMovies(fakeRepository.getTopMovies())
        }

        val size = homeViewModel.listTopMovies.getOrAwaitListTest().getDataIfNotHandledYet()?.data?.size

        assertThat(size).isEqualTo(3)
    }

    /**
     * Test for Popular Movie List in Home View Model
     */
    @Test
    fun `observe popular movie list, where list is empty, return status ERROR`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setPopularMoviesList(emptyList())

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setPopularMovies(fakeRepository.getPopularMovies())
        }

        val value = homeViewModel.listPopularMovies.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `observe popular movie list, where list is not empty, return status SUCCESS`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setPopularMoviesList(listOf(data1, data2, data3))

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setPopularMovies(fakeRepository.getPopularMovies())
        }

        val value = homeViewModel.listPopularMovies.getOrAwaitListTest()
        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `ensure the number of popular movie list as expected`() {

        // Set movie list in fake repository
        // Which has 3 data
        fakeRepository.setPopularMoviesList(listOf(data1, data2, data3))

        // Set value of listPopularMovies in ViewModel
        mainCoroutineScope.launch {
            homeViewModel.setPopularMovies(fakeRepository.getPopularMovies())
        }

        val size = homeViewModel.listPopularMovies.getOrAwaitListTest().getDataIfNotHandledYet()?.data?.size

        assertThat(size).isEqualTo(3)
    }

    /**
     * Test for Popular TV SHow List in Home View Model
     */
    @Test
    fun `observe tv show list, where list is empty, return status ERROR`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setPopularTvShowsList(emptyList())

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setPopularTvShows(fakeRepository.getPopularTvShows())
        }

        val value = homeViewModel.listPopularTvShows.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `observe movie list, where movie list is not empty, return status SUCCESS`() {

        // Set movie list in fake repository to empty list
        fakeRepository.setPopularTvShowsList(listOf(data1, data2, data3))

        // Set value of listMovies in View Model
        mainCoroutineScope.launch {
            homeViewModel.setPopularTvShows(fakeRepository.getPopularTvShows())
        }

        val value = homeViewModel.listPopularTvShows.getOrAwaitListTest()
        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `ensure the number of popular tv show list as expected`() {

        // Set movie list in fake repository
        // Which has 3 data
        fakeRepository.setPopularTvShowsList(listOf(data1, data2, data3))

        // Set value of listPopularTvShow in ViewModel
        mainCoroutineScope.launch {
            homeViewModel.setPopularTvShows(fakeRepository.getPopularTvShows())
        }

        val size = homeViewModel.listPopularTvShows.getOrAwaitListTest().getDataIfNotHandledYet()?.data?.size

        assertThat(size).isEqualTo(3)
    }
}