package com.example.moviejam.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.TestLocalDataSource
import com.example.moviejam.getOrAwaitListTest
import com.google.common.truth.Truth.assertThat
import com.example.moviejam.repository.FakeRepository
import com.example.moviejam.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScope = MainCoroutineRule()

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var fakeRepository: FakeRepository
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        localDataSource = TestLocalDataSource()
        movieDetailViewModel = MovieDetailViewModel(fakeRepository, localDataSource)
    }

    @Test
    fun `observe movie detail, when the detail is already set`() {

        // Set data in view model
        // Simulate the passed id is "1"
        mainCoroutineScope.launch {
            movieDetailViewModel.setData(1)
        }

        // Observe live data
        val value = movieDetailViewModel.dataDetail.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `observe movie detail, when the detail is not set`() {

        // Set data in view model
        // Simulate the passed id is "0", where it means
        // data will be not found in repository
        mainCoroutineScope.launch {
            movieDetailViewModel.setData(0)
        }

        // Observe live data
        val value = movieDetailViewModel.dataDetail.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }
}