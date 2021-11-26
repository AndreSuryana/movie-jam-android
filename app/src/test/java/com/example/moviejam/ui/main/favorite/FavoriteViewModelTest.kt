package com.example.moviejam.ui.main.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.local.entity.FavoriteEntity
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
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<FavoriteEntity>>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private lateinit var viewModel: FavoriteViewModel

    private val dummyFavoriteMovies = DummyData.dummyFavoriteMovies
    private val dummyFavoriteTvShows = DummyData.dummyFavoriteTvShows

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getFavoriteMovies should be success`() {
        val movies = PagedTestDataSources.snapshot(dummyFavoriteMovies)
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(movies)

        `when`(fakeMovieJamRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data).isEqualTo(expectedValue?.data)
        assertThat(actualValue?.data?.size).isEqualTo(expectedValue?.data?.size)
    }

    @Test
    fun `getFavoriteMovies should be success but data is empty`() {
        val movies = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(movies)

        `when`(fakeMovieJamRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteMovies().value?.data?.size
        val expectedValueDataSize = 0

        assertThat(actualValueDataSize).isEqualTo(expectedValueDataSize)
    }

    @Test
    fun `getFavoriteMovies should be error`() {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getFavoriteMovies().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun `getFavoriteTvShows should be success`() {
        val tvShows = PagedTestDataSources.snapshot(dummyFavoriteTvShows)
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(tvShows)

        `when`(fakeMovieJamRepository.getListFavoriteTvShows()).thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteTvShows().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data).isEqualTo(expectedValue?.data)
        assertThat(actualValue?.data?.size).isEqualTo(expectedValue?.data?.size)
    }

    @Test
    fun `getFavoriteTvShows should be success but data is empty`() {
        val tvShows = PagedTestDataSources.snapshot()
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(tvShows)

        `when`(fakeMovieJamRepository.getListFavoriteTvShows()).thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteTvShows().value?.data?.size
        val expectedValueDataSize = 0

        assertThat(actualValueDataSize).isEqualTo(expectedValueDataSize)
    }

    @Test
    fun `getFavoriteTvShows should be error`() {
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getListFavoriteTvShows()).thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getFavoriteTvShows().value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun deleteFromFavorites() = runBlockingTest {
        val id = 580489
        val listFavorites = ArrayList<FavoriteEntity>(dummyFavoriteMovies)

        fakeMovieJamRepository.deleteFromFavorites(id)
        listFavorites.removeIf { it.id == id}

        val movies = PagedTestDataSources.snapshot(dummyFavoriteMovies)
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(movies)

        `when`(fakeMovieJamRepository.getListFavoriteMovies()).thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data).isEqualTo(expectedValue?.data)
        assertThat(actualValue?.data?.size).isEqualTo(expectedValue?.data?.size)
    }

    class PagedTestDataSources private constructor(private val items: List<FavoriteEntity>) : PositionalDataSource<FavoriteEntity>() {
        companion object {
            fun snapshot(items: List<FavoriteEntity> = listOf()): PagedList<FavoriteEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<FavoriteEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FavoriteEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}