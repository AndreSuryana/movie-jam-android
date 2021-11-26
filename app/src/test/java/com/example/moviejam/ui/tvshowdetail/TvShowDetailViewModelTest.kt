package com.example.moviejam.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.ui.main.favorite.FavoriteViewModelTest
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.PagedListUtil
import com.example.moviejam.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvShowDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<TvShowDetailResponse>>

    @Mock
    private lateinit var favoriteObserver: Observer<Resource<PagedList<FavoriteEntity>>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private val local = Mockito.mock(LocalDataSource::class.java)

    private lateinit var viewModel: TvShowDetailViewModel

    private val dummyDetailTvShow = DummyData.dummyDetailTvShow
    private val dummyFavoriteTvShows = DummyData.dummyFavoriteTvShows

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getData should be success`() = runBlockingTest {
        val id = 83095

        val expected = MutableLiveData<Resource<TvShowDetailResponse>>()
        expected.value = Resource.success(dummyDetailTvShow)

        `when`(fakeMovieJamRepository.getTvShowDetail(id.toString())).thenReturn(expected)

        viewModel.getData(id).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getData(id).value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.id).isEqualTo(expectedValue?.data?.id)
    }

    @Test
    fun `getData should be error`() = runBlockingTest {
        val id = 238
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<TvShowDetailResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getTvShowDetail(id.toString())).thenReturn(expected)

        viewModel.getData(id).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getData(id).value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun addTvShowToFavorites() = runBlockingTest {
        val favoriteTvShow = FavoriteEntity(
            83095,
            "/6cXf5EDwVhsRv8GlBzUTVnWuk8Z.jpg",
            "The Rising of the Shield Hero",
            9.0,
            "2019-01-09",
            false
        )
        val favoriteList = listOf(favoriteTvShow)

        fakeMovieJamRepository.insertToFavorites(favoriteTvShow)

        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        Mockito.lenient().`when`(local.getListFavoriteTvShows()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteTvShows()

        val favoriteMovies = Resource.success(PagedListUtil.mockPagedList(favoriteList))
        verify(fakeMovieJamRepository).getListFavoriteTvShows()

        assertThat(favoriteMovies.data).isNotNull()
        assertThat(favoriteMovies.data?.size).isEqualTo(favoriteList.size)
    }

    @Test
    fun checkFavoriteTvShow() = runBlockingTest {
        val id = 29883
        val mockedReturnValue = 1

        `when`(fakeMovieJamRepository.checkFavoriteTvShows(id)).thenReturn(mockedReturnValue)

        val actualValue = viewModel.checkFavoriteTvShow(id)

        assertThat(actualValue).isNotNull()
        assertThat(actualValue).isEqualTo(mockedReturnValue)
    }

    @Test
    fun deleteTvShowFromFavorites() = runBlockingTest {
        val id = 26537
        val listFavorites = ArrayList<FavoriteEntity>(dummyFavoriteTvShows)

        fakeMovieJamRepository.deleteFromFavorites(id)
        listFavorites.removeIf { it.id == id}

        val movies = FavoriteViewModelTest.PagedTestDataSources.snapshot(dummyFavoriteTvShows)
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(movies)

        `when`(fakeMovieJamRepository.getListFavoriteTvShows()).thenReturn(expected)

        fakeMovieJamRepository.getListFavoriteTvShows().observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = fakeMovieJamRepository.getListFavoriteTvShows().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data).isEqualTo(expectedValue?.data)
        assertThat(actualValue?.data?.size).isEqualTo(expectedValue?.data?.size)
    }
}