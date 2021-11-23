package com.example.moviejam.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviejam.MainCoroutineRule
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.TestLocalDataSource
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
class TvShowDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineScope = MainCoroutineRule()

    private lateinit var tvShowDetailViewModel: TvShowDetailViewModel
    private lateinit var fakeRepository: FakeRepository
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        localDataSource = TestLocalDataSource()
        tvShowDetailViewModel = TvShowDetailViewModel(fakeRepository, localDataSource)
    }

    @Test
    fun `observe tv show detail, when the detail is already set`() {

        // Set data in view model
        // Simulate the passed id is "1"
        mainCoroutineScope.launch {
            tvShowDetailViewModel.setData(1)
        }

        // Observe live data
        val value = tvShowDetailViewModel.dataDetail.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `observe tv show detail, when the detail is not set`() {

        // Set data in view model
        // Simulate the passed id is "0", where it means
        // data will be not found in repository
        mainCoroutineScope.launch {
            tvShowDetailViewModel.setData(0)
        }

        // Observe live data
        val value = tvShowDetailViewModel.dataDetail.getOrAwaitListTest()

        assertThat(value.getDataIfNotHandledYet()?.status).isEqualTo(Status.ERROR)
    }
}